package com.bitty.notifyme;

import java.util.ArrayList;

import com.bitty.utils.Convert;

import android.app.AlarmManager;
import android.app.ListActivity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class DailyNotificationsActivity extends ListActivity
{

	private TextView dayText;
	private Button returnHomeButton;
	private Context mContext;
	private ArrayList<NotifyMeItem> notifyMeArray;
	private ArrayList<DailyNotificationsItem> itemArray = new ArrayList<DailyNotificationsItem>();

	private NotificationAdapter adapter;

	private MyBroadCastReceiver receiver;

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.daily_notifications);

		dayText = (TextView) findViewById(R.id.day_text);
		returnHomeButton = (Button) findViewById(R.id.return_home_btn);

		Typeface font = Typeface.createFromAsset(this.getAssets(), "fonts/VarelaRound-Regular.ttf");
		Typeface font2 = Typeface.createFromAsset(this.getAssets(), "fonts/DINEngschrift-Regular.ttf");
		dayText.setTypeface(font);
		returnHomeButton.setTypeface(font2);

		returnHomeButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v)
			{
				finish();
			}
		});

		notifyMeArray = ((NotifyApplication) getApplication()).getCurrentDaysNotifications();
		adapter = new NotificationAdapter(this);
		this.setListAdapter(adapter);

		dayText.setText(((NotifyApplication) getApplication()).getCurrentDay());
	
		receiver = new MyBroadCastReceiver();
	}

	@Override
	protected void onResume()
	{
		super.onResume();

		adapter.notifyDataSetChanged();
		registerReceiver(receiver, new IntentFilter(DailyNotificationsItem.DELETE_ITEM));
	}

	@Override
	protected void onPause()
	{
		super.onPause();
		unregisterReceiver(receiver);
	}

	private void onDeleteDailyNotificationItem(int _arrayIndex, long _db_ID)
	{
		long dbID = _db_ID;
		
		//Delete from daily array
		notifyMeArray.remove(_arrayIndex);
		
		//Delete from db and remove reminder
		NotifyDBAdapter notifyDB = ((NotifyApplication) getApplication()).getNotifyDB();
		notifyDB.removeNotification(dbID);
		notifyDB.open();

		//Remove alert reminder. Alarm Id needs to be a reference to the database key
		Intent intent = new Intent(this, AlarmReceiver.class);
		intent.putExtra("alarm_id", dbID);
		PendingIntent pedingIntent = PendingIntent.getBroadcast(this, Convert.safeLongToInt(dbID), intent,
				PendingIntent.FLAG_UPDATE_CURRENT);

		//Cancel alarm
		AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
		alarmManager.cancel(pedingIntent);

		adapter.notifyDataSetChanged();
		
		if(notifyMeArray.size() == 0)
			finish();
	}

	/*
	 * Adapter for Daily Notification Items
	 */
	public class NotificationAdapter extends BaseAdapter
	{
		public NotificationAdapter(Context c)
		{
			mContext = c;
		}

		public int getCount()
		{
			return notifyMeArray.size();
		}

		public Object getItem(int position)
		{
			return itemArray.get(position);
		}

		public long getItemId(int position)
		{
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent)
		{
			NotifyMeItem notifyItem = (NotifyMeItem) notifyMeArray.get(position);

			DailyNotificationsItem item = new DailyNotificationsItem(mContext);
			item.setDB_ID(notifyItem.getDB_ID());
			item.setArrayIndex(position);
			item.setContent(notifyItem.getHour(), notifyItem.getMinutes(), notifyItem.getSubways());
			itemArray.add(item);
			return item;
		}
	}

	/*
	 * Broadcast receiver
	 */
	public class MyBroadCastReceiver extends BroadcastReceiver
	{
		@Override
		public void onReceive(Context context, Intent intent)
		{
			// TODO Auto-generated method stub
			int arrayIndex = intent.getIntExtra("array_index", -1);
			long db_ID = intent.getLongExtra("db_ID", -1);
			onDeleteDailyNotificationItem(arrayIndex, db_ID);
		}
	}
}
