package com.bitty.notifyme;

import java.util.ArrayList;
import java.util.List;

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
	
	private MainDayItem mondayBox, tuesdayBox, wednesdayBox, thursdayBox, fridayBox, saturdayBox, sundayBox;
	private MainDayItem[] dayBoxArray = { mondayBox, tuesdayBox, wednesdayBox, thursdayBox, fridayBox, saturdayBox,
			sundayBox };
	private List<String> dayOfWeekArray = new ArrayList<String>();
	private LinearLayout holder;
	private LinearLayout addNotificationButton;
	private TextView addText;
	private NotifyInfoDialog notificationChooser;
	
	private NotifyMeDBAdapter notifyDBAdapter;
	private Cursor notifyCursor;

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		//"NotifyMe title text
		TextView titleText = (TextView) findViewById(R.id.title);
		Typeface font = Typeface.createFromAsset(getAssets(), "fonts/VarelaRound-Regular.ttf");
		titleText.setTypeface(font);

		createAddNotifyBtn();
		
		notifyDBAdapter = new NotifyMeDBAdapter(this);
		notifyDBAdapter.open();
		populateNotifyList();
		
	}
	
	@Override
	protected void onStart() {
		Log.w(TAG, "onStart");
		super.onStart();
	}

	@Override
	protected void onResume() {
		Log.w(TAG, "onResume");
		super.onResume();
	}

	@Override
	protected void onPause() {
		Log.w(TAG, "onPause");
		super.onPause();
	}

	@Override
	protected void onStop() {
		Log.w(TAG, "onStop");
		super.onStop();
	}
	
	
	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		
		//close DB
		notifyDBAdapter.close();
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
		notifyCursor = notifyDBAdapter.getAllNotifyItemsCursor();
		startManagingCursor(notifyCursor);
		notifyCursor.requery();
		
		Resources res = this.getResources();
		String[] days = res.getStringArray(R.array.days_array);

		for (int i = 1; i < days.length; i++)
		{
			dayOfWeekArray.add(days[i]);
		}

		dayOfWeekArray.add(days[0]);

		holder = (LinearLayout) findViewById(R.id.days_holder);

		for (int i = 0; i < dayBoxArray.length; i++)
		{
			dayBoxArray[i] = new MainDayItem(this);
			dayBoxArray[i].init(dayOfWeekArray.get(i).toUpperCase());

			holder.addView(dayBoxArray[i]);

			dayBoxArray[i].setOnClickListener(new OnClickListener()
			{
				public void onClick(View arg0)
				{
					handleDayBoxClick((MainDayItem) arg0);
				}
			});
		}
	}
	
	private void handleDayBoxClick(MainDayItem daybox)
	{
		if (daybox.notifications > 0)
		{
			notificationChooser = new NotifyInfoDialog(this);
			notificationChooser.passInData(daybox.dayOfWeek);
			// need to pass in all the data for that day of the week - TO DO!
			notificationChooser.show();
		}
	}

	private void createReminder()
	{
		Intent i = new Intent(this, AddEditActivity.class);
		startActivityForResult(i, ACTIVITY_CREATE);
	}
}