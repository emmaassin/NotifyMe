package com.bitty.notifyme;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity
{
	private static final String TAG = "MainActivity";
	
	private static final int ACTIVITY_CREATE = 0;
	private static final int ACTIVITY_EDIT = 1;
	
	//These days represent the calendar ids for each day
	private static final int MONDAY = 2;
	private static final int TUESDAY = 3;
	private static final int WEDNESDAY = 4;
	private static final int THURSDAY= 5;
	private static final int FRIDAY = 6;
	private static final int SATURDAY = 7;
	private static final int SUNDAY = 1;
	
	private int[] dayDBValue = {MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY};
	
	private MainDayItem mondayBox, tuesdayBox, wednesdayBox, thursdayBox, fridayBox, saturdayBox, sundayBox;
	private MainDayItem[] dayItemsArray = { mondayBox, tuesdayBox, wednesdayBox, thursdayBox, fridayBox, saturdayBox,
			sundayBox };
	private LinearLayout holder;
	private LinearLayout addNotificationButton;
	private TextView addText;
	private DailyNotificationsDialog notificationChooser;
	
	private NotifyMeDBAdapter notifyDB;

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		//"NotifyMe title text
		TextView titleText = (TextView) findViewById(R.id.title);
		TextView infoHeader = (TextView) findViewById(R.id.info_header);
		TextView infoBody = (TextView) findViewById(R.id.info_body);
		
		Typeface font = Typeface.createFromAsset(getAssets(), "fonts/VarelaRound-Regular.ttf");
		titleText.setTypeface(font);
		infoHeader.setTypeface(font);
		infoBody.setTypeface(font);

		createAddNotifyBtn();
		
		holder = (LinearLayout) findViewById(R.id.days_holder);

		notifyDB = new NotifyMeDBAdapter(this);
		notifyDB.open();
	}
	
	@Override
	protected void onStart() {
		//Log.w(TAG, "onStart");
		super.onStart();
	}

	@Override
	protected void onResume() {
		Log.w(TAG, "onResume");
		super.onResume();
		notifyDB.open();
		populateNotifyList();
	}

	@Override
	protected void onPause() {
		Log.w(TAG, "onPause");
		super.onPause();
		notifyDB.close();
	}

	@Override
	protected void onStop() {
		Log.w(TAG, "onStop");
		super.onStop();
		
		if(notifyDB.isOpen())
			notifyDB.close();
	}
	
	
	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		
		//close DB
		notifyDB.close();
	}
	
	private void createAddNotifyBtn()
	{
		addNotificationButton = (LinearLayout) findViewById(R.id.add);
		addText = (TextView) findViewById(R.id.add_text);
		Typeface font2 = Typeface.createFromAsset(getAssets(), "fonts/DINEngschrift-Regular.ttf");
		addText.setTypeface(font2);

		addNotificationButton.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				createReminder();
			}
		});
	}
	
	//TODO: Get total notifications for each day
	//and parse to arrays by day
	private void populateNotifyList()
	{
		Resources res = this.getResources();
		String[] dayNames = res.getStringArray(R.array.days_array);

		if(holder.getChildCount() > 0)
			holder.removeAllViews();
		
		for (int i = 0; i < dayItemsArray.length; i++)
		{
			int dayCount = notifyDB.getDayCount(dayDBValue[i]);
			
			dayItemsArray[i] = new MainDayItem(this);
			dayItemsArray[i].init(dayNames[i].toUpperCase(), dayDBValue[i], dayCount);

			holder.addView(dayItemsArray[i]);

			dayItemsArray[i].setOnClickListener(new OnClickListener()
			{
				public void onClick(View view)
				{
					onDayClick((MainDayItem) view);
				}
			});
		}
	}
	
	private void onDayClick(MainDayItem dayItem)
	{
		if (dayItem.notifyCount > 0)
		{
			ArrayList<NotifyMeItem> notifyItemsByDay = notifyDB.getNotifyItemsByDay(dayItem.dayDBValue, this);
			notificationChooser = new DailyNotificationsDialog(this);
			notificationChooser.passInData(dayItem.getDayName(), notifyItemsByDay);
			notificationChooser.show();
		}
	}

	private void createReminder()
	{
		Intent i = new Intent(this, AddEditActivity.class);
		startActivityForResult(i, ACTIVITY_CREATE);
	}
}