package org.cortelyoucollective.notifyme;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.json.JSONObject;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class ReminderService extends WakeReminderIntentService
{

	private static final String TAG = "ReminderService";
	private long notificationID;
	private CurrentStatusLookupTask lastLookup = null;

	private ArrayList<String> notificatonList = new ArrayList<String>();
	private MTAStatusItemList mtaStatusArray = null;

	private NotifyDBAdapter notifyDB;

	private String notificationTitle = "";
	private NotifyMeItem notifyMeItem;
	private List<String> subwayLines;
	public String transitType;

	private SharedPreferences settings;

	public ReminderService()
	{
		super("ReminderService");
		try
		{
			Class.forName("android.os.AsyncTask");
		} catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}

		Log.w(TAG, "constructor");
	}

	@Override
	void doReminderWork(Intent intent)
	{
		Log.w(TAG, "doReminderWork");
		// app reaches out to MTA service if there's currently a delay on the
		// line in the notification
		// we can store a reference to the line in the extras once we've grabbed
		// that data, if there's something to report, do the
		// notify code be

		notificationID = intent.getExtras().getLong("alarm_id");

		// GET DB ITEM
		notifyDB = ((NotifyApplication) getApplication()).getNotifyDB();
		notifyMeItem = notifyDB.getNotification(notificationID);
		subwayLines = notifyMeItem.getTrains();
		transitType = notifyMeItem.getTrainType();

		loadMTAFeed();
	}

	/*
	 * Create a thread to parse the MTA feed
	 */
	private void loadMTAFeed()
	{
		if (lastLookup == null || lastLookup.getStatus().equals(AsyncTask.Status.FINISHED))
		{
			Log.i(TAG, "loadMTAFeed");
			lastLookup = new CurrentStatusLookupTask();
			lastLookup.execute((Void[]) null);
		}
	}

	/*
	 * This function should check if a notification should occur by comparing
	 * the users notifications with the data
	 */
	private void announceNewStatusItem(ArrayList<String> arr)
	{
		for (int i = 0; i < subwayLines.size(); i++)
		{
			if (subwayLines.get(i).equals(arr.get(0)))
			{
				if (!arr.get(1).equals("GOOD SERVICE"))
				{
					if (mtaStatusArray == null)
						mtaStatusArray = new MTAStatusItemList();

					MTAStatusItem item = new MTAStatusItem(arr.get(0), arr.get(1), arr.get(2), arr.get(3), arr.get(4),
							transitType);
					mtaStatusArray.add(item);
					notificationTitle += " " + arr.get(0) + " ";
					notificatonList.add(arr.get(0));

					// Log.w(TAG, "line" + arr.get(0));
					// Log.w(TAG, "status" + arr.get(1));
					// Log.w(TAG, "status text" + arr.get(2));
					// Log.w(TAG, "date" + arr.get(3));
					// Log.w(TAG, "time" + arr.get(4));
				}
			}
		}
	}

	/*
	 * Only called if notification is required
	 */
	private void sendNotification()
	{
		// create notification
		Bundle b = new Bundle();
		b.putParcelable("MTA_status_data", mtaStatusArray);
		Intent mtaIntent = new Intent(this, MTACurrentStatusActivity.class);
		mtaIntent.putExtras(b);
		// add parcel as extra
		//mtaIntent.putParcelableArrayListExtra("MTA_status_data", mtaStatusArray);
		
		// trying with serializing
		//Bundle extras = new Bundle();
		//extras.putSerializable("MTA_status_data", mtaStatusArray);
		//mtaIntent.putExtras(extras);
		
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, mtaIntent, PendingIntent.FLAG_ONE_SHOT);
		Notification notification = new Notification(R.drawable.notify_icon, "NOTIFY ME NYC ALERT!", System
				.currentTimeMillis());
		notification.setLatestEventInfo(this, "FOR THE FOLLOWING TRAINS:", notificationTitle, contentIntent);
		notification.flags |= Notification.FLAG_AUTO_CANCEL;

		settings = getSharedPreferences("NotifyMeSettings", MODE_PRIVATE);
		if (settings.contains("tone"))
		{
			if (settings.getBoolean("tone", true))
				notification.defaults |= Notification.DEFAULT_SOUND;

		} else
		{
			notification.defaults |= Notification.DEFAULT_SOUND;
		}

		if (settings.contains("vibration"))
		{
			if (settings.getBoolean("vibration", true))
				notification.defaults |= Notification.DEFAULT_VIBRATE;

		} else
		{
			notification.defaults |= Notification.DEFAULT_VIBRATE;
		}

		// Using notification of 0 instead of notificationID
		// No other notification will be called in this app
		NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		notificationManager.notify(0, notification);
	}

	@Override
	public IBinder onBind(Intent intent)
	{
		return null;
	}

	private void announceUpdateEnd()
	{
		Log.w(TAG, "announceUpdateEnd NOTIFICATION WON'T FIRE IF THIS IS NOT CALLED ");
		if (notificatonList.size() > 0)
		{
			// commented out to see if the parcel worked
			//NotifyApplication app = (NotifyApplication) getApplication();
			//app.setMTAStatusArray(mtaStatusArray);
			
			sendNotification();
		}
	}

	/*
	 * Create separate thread for MTA feed parsing
	 */
	private class CurrentStatusLookupTask extends AsyncTask<Void, Void, Void>
	{
		private static final String TAG = "CurrentStatusLookupTask";

		@Override
		protected Void doInBackground(Void... params)
		{
			try
			{
				/** Handling XML */
				SAXParserFactory spf = SAXParserFactory.newInstance();
				SAXParser sp = spf.newSAXParser();

				/** Send URL to parse XML Tags */
				URL url = new URL(getString(R.string.mta_feed));
				InputStream stream = url.openStream();

				/** Create handler to handle XML Tags ( extends DefaultHandler ) */
				MyXMLHandler myXMLHandler = new MyXMLHandler();

				XMLReader xr = sp.getXMLReader();
				xr.setContentHandler(myXMLHandler);
				xr.parse(new InputSource(stream));
				stream.close();

			} catch (Exception e)
			{
				Log.w(TAG, "XML Parsing Excpetion = " + e.getMessage());
			}

			return null;
		}

		@Override
		protected void onProgressUpdate(Void... values)
		{
			super.onProgressUpdate(values);
			Log.w(TAG, "onProgressUpdate");
		}

		@Override
		protected void onPreExecute()
		{
			super.onPreExecute();
			Log.w(TAG, "onPreExecute");
		}

		@Override
		protected void onPostExecute(Void result)
		{
			super.onPostExecute(result);
			Log.w(TAG, "onPostExecute");
			stopSelf();
			announceUpdateEnd();
		}
	}

	public class EndOfProcessingException extends SAXException
	{
		private static final long serialVersionUID = 1L;
		private static final String TAG = "EndOfProcessingException";

		public EndOfProcessingException(String msg)
		{
			super(msg);
			Log.w(TAG, " EndOfProcessingException :: STOP PARSING XML");
			announceUpdateEnd();
		}
	}

	/*
	 * 
	 */
	public class MyXMLHandler extends DefaultHandler
	{
		private static final String TAG = "MyXMLHandler";

		String currentValue = null;
		String currentCategory = null;
		String buffer = "";
		String currentQName = null;
		ArrayList<String> mtaItemArr = new ArrayList<String>();

		// Pattern pattern;

		@Override
		public void startDocument() throws SAXException
		{
			// pattern = Pattern.compile("000000");
			Log.w(TAG, " startDocument for trainType = " + transitType);
		}

		@Override
		public void endDocument() throws SAXException
		{
			Log.w(TAG, " endDocument");
		}

		@Override
		public void startElement(String namespaceURI, String localName, String qName, Attributes atts)
				throws SAXException
		{
			// Log.w(TAG, "startElement");
			if (localName.equals(transitType))
				currentCategory = localName;

			if (localName.equals("text"))
				buffer = "";
		}

		@Override
		public void characters(char ch[], int start, int length)
		{
			currentValue = new String(ch, start, length);
			buffer += currentValue;
		}

		@Override
		public void endElement(String namespaceURI, String localName, String qName) throws SAXException
		{
			if (localName.equals("name") || localName.equals("status") || localName.equals("Date")
					|| localName.equals("Time"))
			{
				mtaItemArr.add(currentValue);
			}

			if (localName.equals("text"))
			{
				mtaItemArr.add(buffer);
			}

			if (localName.equals("line"))
			{
				mtaItemArr.add(currentCategory);
				announceNewStatusItem(mtaItemArr);
				mtaItemArr.clear();
			}

			if (localName.equals(transitType))
			{
				Log.w(TAG, " endElement for trainType = " + transitType);

				currentValue = null;
				currentCategory = null;
				buffer = "";
				currentQName = null;
				mtaItemArr = null;
				// throw new EndOfProcessingException("Done.");
			}
		}
	}
}
