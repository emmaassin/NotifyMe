package com.bitty.notifyme;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AddEditActivity extends Activity
{
	private static final String TAG = "AddEditActivity";

	private TextView title, trainsText, daysText, title2;
	private LinearLayout subwayButton, daysButton;
	private TimeChooser timePicker;
	private Button saveButton, cancelButton;
	private SelectDayDialog daysDialog;
	private SelectSubwayDialog subwayDialog;
	private ImageView trainsCheck, daysCheck;

	public List<String> subwaySelected = new ArrayList<String>();
	public List<Integer> daysSelectedArr = new ArrayList<Integer>();
	
	private NotifyMeDBAdapter notifyDB;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editscreen);
		
		notifyDB = new NotifyMeDBAdapter(this);
		

		title = (TextView) findViewById(R.id.edit_title1);
		Typeface font = Typeface.createFromAsset(this.getAssets(), "fonts/VarelaRound-Regular.ttf");
		title.setTypeface(font);
		trainsText = (TextView) findViewById(R.id.trains_text);
		daysText = (TextView) findViewById(R.id.days_text);
		title2 = (TextView) findViewById(R.id.edit_title2);
		Typeface font2 = Typeface.createFromAsset(this.getAssets(), "fonts/DINEngschrift-Regular.ttf");
		trainsText.setTypeface(font2);
		daysText.setTypeface(font2);
		title2.setTypeface(font2);

		trainsCheck = (ImageView) findViewById(R.id.trains_check);
		daysCheck = (ImageView) findViewById(R.id.days_check);

		timePicker = (TimeChooser) findViewById(R.id.time_picker);
		timePicker.initComponent(getApplicationContext());

		saveButton = (Button) findViewById(R.id.save_button);
		saveButton.setTypeface(font2);
		cancelButton = (Button) findViewById(R.id.cancel_button);
		cancelButton.setTypeface(font2);
		subwayButton = (LinearLayout) findViewById(R.id.trains);
		daysButton = (LinearLayout) findViewById(R.id.days);

		saveButton.setEnabled(true);
		saveButton.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View view)
			{
				saveState();
				setResult(RESULT_OK);
				Toast
						.makeText(AddEditActivity.this, getString(R.string.notification_saved_message),
								Toast.LENGTH_SHORT).show();
				
			}
		});

		cancelButton.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View view)
			{
				Toast.makeText(AddEditActivity.this, getString(R.string.notification_cancel_message),
						Toast.LENGTH_SHORT).show();
				finish();
			}
		});

		daysButton.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				createDaysPopup();
			}
		});

		subwayButton.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				createSubwayPopUp();
			}
		});
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
		notifyDB.open();
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
	
	private void createSubwayPopUp()
	{
		subwayDialog = new SelectSubwayDialog(this);
		if (subwaySelected.size() > 0)
			subwayDialog.setAlreadyChecked(subwaySelected);

		subwayDialog.show();
		subwayDialog.saveButton.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				subwaySelected = subwayDialog.getCheckedLinessArray();
				if (subwaySelected.size() > 0)
				{
					trainsCheck.setImageResource(R.drawable.check);
				} else
				{
					trainsCheck.setImageResource(R.drawable.add);
					saveButton.setEnabled(false);
				}
				subwayDialog.cancel();
			}
		});
	}

	private void createDaysPopup()
	{
		daysDialog = new SelectDayDialog(this);
		if (daysSelectedArr.size() > 0)
			daysDialog.setAlreadyChecked(daysSelectedArr);

		daysDialog.show();
		daysDialog.saveButton.setOnClickListener(new View.OnClickListener()
		{

			public void onClick(View v)
			{
				daysSelectedArr = daysDialog.getCheckedDaysArray();
				if (daysSelectedArr.size() > 0)
				{
					daysCheck.setImageResource(R.drawable.check);
					if (subwaySelected.size() > 0)
					{
						saveButton.setEnabled(true);
					}
				} else
				{
					daysCheck.setImageResource(R.drawable.add);
				}
				daysDialog.cancel();
			}
		});
	}

	private void saveState()
	{
		//Log.w(TAG, "saveState");
		ReminderManager reminderMgr = new ReminderManager(getApplicationContext());
		int hour = timePicker.getCurrentHour();
		int minute = timePicker.getCurrentMinute();
		
		// set alerts for each day in the day array
		for (int i = 0; i < daysSelectedArr.size(); i++)
		{
			// insert to database and serialize subway lines array
			int daySelected = daysSelectedArr.get(i);
			NotifyMeItem item = new NotifyMeItem(subwaySelected, daySelected , hour, minute);
			long alarmID = notifyDB.insertNewNotification(item, this);
			
			reminderMgr.setReminder(hour, minute, daySelected, alarmID);
			// Log.w(TAG, "day = "+ daysSelectedArr.get(i));
		}
		
		
		finish();
	}
}
