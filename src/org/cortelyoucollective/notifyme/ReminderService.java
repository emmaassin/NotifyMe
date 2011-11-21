package org.cortelyoucollective.notifyme;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.cortelyoucollective.notifyme.R;
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
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;

public class ReminderService extends WakeReminderIntentService
{
	private static final String TAG = "ReminderService";
	private long notificationID;
	private CurrentStatusLookupTask lastLookup = null;

	private ArrayList<String> notificatonList = new ArrayList<String>();
	private ArrayList<MTAStatusItem> mtaStatusArray = null;

	private NotifyDBAdapter notifyDB;

	private String notificationTitle = "";
	private NotifyMeItem notifyMeItem;
	
	private SharedPreferences settings;

	public ReminderService()
	{
		super("ReminderService");
		Log.w(TAG, "constructor");
	}

	@Override
	void doReminderWork(Intent intent)
	{
		Log.w(TAG, "doReminderWork");
		// app reaches out to MTA service if there's currently a delay on the
		// line in the notification
		// we can store a reference to the line in the extras
		// once we've grabbed that data, if there's something to report, do the
		// notify code be

		notificationID = intent.getExtras().getLong("alarm_id");

		// GET DB ITEM
		notifyDB = ((NotifyApplication) getApplication()).getNotifyDB();
		notifyMeItem = notifyDB.getNotification(notificationID);

		loadMTAFeed();

	}

	// so here is where the app reaches out to the MTA service to find out
	// if there's currently a delay on the line we care about
	// we can store a reference to the line in the extras
	// once we've grabbed that data, if there's something to report, do the
	// notify code below

	private void loadMTAFeed()
	{
		if (lastLookup == null || lastLookup.getStatus().equals(AsyncTask.Status.FINISHED))
		{
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
		List<String> subwayLines = notifyMeItem.getTrains();
		String transitType = notifyMeItem.getTrainType();

		for (int i = 0; i < subwayLines.size(); i++)
		{
			if (subwayLines.get(i).equals(arr.get(0)))
			{
				if (!arr.get(1).equals("GOOD SERVICE"))
				{
					if (mtaStatusArray == null)
						mtaStatusArray = new ArrayList<MTAStatusItem>();

					mtaStatusArray.add(new MTAStatusItem(arr.get(0), arr.get(1), arr.get(2), arr.get(3), arr.get(4), transitType));
					notificationTitle += " " + arr.get(0) + " ";
					notificatonList.add(arr.get(0));

					//Log.w(TAG, "line" + arr.get(0));
					//Log.w(TAG, "status" + arr.get(1));
					//Log.w(TAG, "status text" + arr.get(2));
					//Log.w(TAG, "date" + arr.get(3));
					//Log.w(TAG, "time" + arr.get(4));
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
		Intent mtaIntent = new Intent(this, MTACurrentStatusActivity.class);
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, mtaIntent, PendingIntent.FLAG_ONE_SHOT);
		Notification notification = new Notification(R.drawable.notify_icon, "NOTIFY ME NYC ALERT!", System
				.currentTimeMillis());
		notification.setLatestEventInfo(this, "FOR THE FOLLOWING TRAINS:", notificationTitle, contentIntent);
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		
		settings = getSharedPreferences("NotifyMeSettings", MODE_PRIVATE);
		if(settings.contains("tone"))
		{
			if(settings.getBoolean("tone", true))
			{
				notification.defaults |= Notification.DEFAULT_SOUND;
			}
		} else {
			notification.defaults |= Notification.DEFAULT_SOUND;
		}
		
		if(settings.contains("vibration"))
		{
			if(settings.getBoolean("vibration", true))
			{
				notification.defaults |= Notification.DEFAULT_VIBRATE;
			}
		} else {
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
		// TODO Auto-generated method stub
		return null;
	}

	private void announceUpdateEnd()
	{
		Log.w(TAG, "announceUpdateEnd");
		if (notificatonList.size() > 0)
		{
			NotifyApplication app = (NotifyApplication)getApplication();
			app.setMTAStatusArray(mtaStatusArray);
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
				XMLReader xr = sp.getXMLReader();

				/** Send URL to parse XML Tags */
				URL sourceUrl = new URL(getString(R.string.mta_feed));

				/** Create handler to handle XML Tags ( extends DefaultHandler ) */
				MyXMLHandler myXMLHandler = new MyXMLHandler();
				xr.setContentHandler(myXMLHandler);
				xr.parse(new InputSource(sourceUrl.openStream()));

			} catch (Exception e)
			{
				Log.w(TAG, "XML Pasing Excpetion = " + e.getMessage());
			}

			return null;
		}

		@Override
		protected void onProgressUpdate(Void... values)
		{
			super.onProgressUpdate(values);
		}

		@Override
		protected void onPreExecute()
		{
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(Void result)
		{
			super.onPostExecute(result);
			Log.w(TAG, "onPostExecute");
			stopSelf();
		}
	}

	public class EndOfProcessingException extends SAXException
	{
		private static final long serialVersionUID = 1L;
		private static final String TAG = "EndOfProcessingException";

		public EndOfProcessingException(String msg)
		{
			super(msg);
			//Log.w(TAG, "STOP PARSING XML");
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
		String trainType;
		//Pattern pattern;

		@Override
		public void startDocument() throws SAXException
		{
			//Log.w(TAG, "startDocument");
			//pattern = Pattern.compile("000000");
			trainType = (String) ((NotifyApplication) getApplication()).getCurrentTrainType();
		}

		@Override
		public void endDocument() throws SAXException
		{
		}

		@Override
		public void startElement(String namespaceURI, String localName, String qName, Attributes atts)
				throws SAXException
		{

			if (localName.equals(trainType))
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
				/*
				Matcher matcher = pattern.matcher(buffer);
				if (matcher.find())
				{
					String output = matcher.replaceAll("ffffff");
					mtaItemArr.add(output);
				} else
				{
					mtaItemArr.add(buffer);
				}
				*/
			}

			if (localName.equals("line"))
			{
				mtaItemArr.add(currentCategory);
				announceNewStatusItem(mtaItemArr);
				mtaItemArr.clear();
			}

			if (localName.equals(trainType))
			{
				// Some sort of finishing up work
				currentValue = null;
				currentCategory = null;
				buffer = "";
				currentQName = null;
				mtaItemArr = null;
				throw new EndOfProcessingException("Done.");
			}
		}
	}
}
