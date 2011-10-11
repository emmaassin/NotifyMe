package com.bitty.notifyme;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity
{
	private static final String TAG = "MainActivity";

	// These days represent the calendar ids for each day
	private static final int MONDAY = 2;
	private static final int TUESDAY = 3;
	private static final int WEDNESDAY = 4;
	private static final int THURSDAY = 5;
	private static final int FRIDAY = 6;
	private static final int SATURDAY = 7;
	private static final int SUNDAY = 1;

	private int[] dayDBValue = { MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY };

	private MainDayItem mondayBox, tuesdayBox, wednesdayBox, thursdayBox, fridayBox, saturdayBox, sundayBox;
	private MainDayItem[] dayItemsArray = { mondayBox, tuesdayBox, wednesdayBox, thursdayBox, fridayBox, saturdayBox,
			sundayBox };
	private LinearLayout holder;
	private LinearLayout delaysButton;
	private LinearLayout addNotificationButton;

	private NotifyDBAdapter notifyDB;

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// "NotifyMe title text
		TextView titleText = (TextView) findViewById(R.id.title);
		TextView infoHeader = (TextView) findViewById(R.id.info_header);
		TextView infoBody = (TextView) findViewById(R.id.info_body);
		TextView addText = (TextView) findViewById(R.id.add_text);
		TextView delayText = (TextView) findViewById(R.id.delay_text);

		Typeface font = Typeface.createFromAsset(getAssets(), "fonts/VarelaRound-Regular.ttf");
		titleText.setTypeface(font);
		infoHeader.setTypeface(font);
		infoBody.setTypeface(font);

		Typeface font2 = Typeface.createFromAsset(getAssets(), "fonts/DINEngschrift-Regular.ttf");
		addText.setTypeface(font2);
		delayText.setTypeface(font2);

		createDelaysBtn();
		createAddNotifyBtn();

		holder = (LinearLayout) findViewById(R.id.days_holder);

		notifyDB = ((NotifyApplication) getApplication()).getNotifyDB();
	}

	@Override
	protected void onStart()
	{
		// Log.w(TAG, "onStart");
		super.onStart();
	}

	@Override
	protected void onResume()
	{
		Log.w(TAG, "onResume");
		super.onResume();

		populateNotifyList();
	}

	@Override
	protected void onPause()
	{
		Log.w(TAG, "onPause");
		super.onPause();
	}

	@Override
	protected void onStop()
	{
		Log.w(TAG, "onStop");
		super.onStop();
	}

	@Override
	protected void onDestroy()
	{
		Log.w(TAG, "onDestroy");
		super.onDestroy();
	}

	private void createDelaysBtn()
	{
		delaysButton = (LinearLayout) findViewById(R.id.delays);
		delaysButton.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				// go to MTA site
				Uri uri = Uri.parse("http://mobile.usablenet.com/mt/advisory.mtanyct.info/service/mobile/subway.php"); 
				Intent intent = new Intent(Intent.ACTION_VIEW, uri); 
				startActivity(intent); 
			}
		});
	}

	private void createAddNotifyBtn()
	{
		addNotificationButton = (LinearLayout) findViewById(R.id.add);
		addNotificationButton.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				onAddEditActivity();
			}
		});
	}

	/*
	 * Get total notifications for each day and parse to arrays
	 */
	private void populateNotifyList()
	{
		Resources res = this.getResources();
		String[] dayNames = res.getStringArray(R.array.days_array);

		if (holder.getChildCount() > 0)
			holder.removeAllViews();

		for (int i = 0; i < dayItemsArray.length; i++)
		{
			int dayCount = notifyDB.getDayCount(dayDBValue[i]);

			dayItemsArray[i] = new MainDayItem(this);
			dayItemsArray[i].init(dayNames[i].toUpperCase(), dayDBValue[i], dayCount);
			dayItemsArray[i].setOnClickListener(new OnClickListener()
			{
				public void onClick(View view)
				{
					onDayClick((MainDayItem) view);
				}
			});
			holder.addView(dayItemsArray[i]);
		}
	}

	private void onDayClick(MainDayItem dayItem)
	{
		if (dayItem.notifyCount > 0)
		{
			ArrayList<NotifyMeItem> notificationByDayArray = notifyDB.getNotifyItemsByDay(dayItem.dayDBValue);

			NotifyApplication app = (NotifyApplication) getApplication();
			app.setDailyNotificationArray(notificationByDayArray);
			app.setCurrentDayName(dayItem.getDayName());
			app.setCurrentDayID(dayItem.getDayID());
			
			startActivity(new Intent(this, DailyNotificationsActivity.class));
		}
	}

	private void onAddEditActivity()
	{
		startActivity(new Intent(this, AddEditActivity.class));
	}
}