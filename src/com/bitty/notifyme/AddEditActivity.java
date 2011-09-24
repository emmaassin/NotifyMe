package com.bitty.notifyme;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class AddEditActivity extends Activity
{
	private static final String TAG = "AddEditActivity";
	
	private TextView title, trainsText, daysText, title2;
	private LinearLayout linesButton, daysButton;
	private TimeChooser timePicker;
	private Button saveButton, cancelButton;
	private SelectDayDialog daysDialog;
	private SelectSubwayDialog linesDialog;
	private ImageView trainsCheck, daysCheck;

	public List<String> trainLinesArray = new ArrayList<String>();
	public List<String> daysArray = new ArrayList<String>();
	
	String[] test = {"3", "4"};
		
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editscreen);
		
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
		linesButton = (LinearLayout) findViewById(R.id.trains);
		daysButton = (LinearLayout) findViewById(R.id.days);

		saveButton.setEnabled(true);
		saveButton.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View view)
			{
				saveState();
				setResult(RESULT_OK);
				Toast.makeText(AddEditActivity.this, getString(R.string.notification_saved_message),
						Toast.LENGTH_SHORT).show();
				finish();
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
				makeDaysPopup();
			}
		});

		linesButton.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				makeLinesPopup();
			}
		});
	}

	private void makeLinesPopup()
	{
		linesDialog = new SelectSubwayDialog(this);
		if (trainLinesArray.size() > 0)
			linesDialog.setAlreadyChecked(trainLinesArray);

		linesDialog.show();
		linesDialog.okButton.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				trainLinesArray = linesDialog.getCheckedLinessArray();
				if (trainLinesArray.size() > 0)
				{
					trainsCheck.setImageResource(R.drawable.check);
				} else
				{
					trainsCheck.setImageResource(R.drawable.add);
					saveButton.setEnabled(false);
				}
				linesDialog.cancel();
			}
		});
	}

	private void makeDaysPopup()
	{
		daysDialog = new SelectDayDialog(this);
		if (daysArray.size() > 0)
			daysDialog.setAlreadyChecked(daysArray);

		daysDialog.show();
		daysDialog.okButton.setOnClickListener(new View.OnClickListener()
		{

			public void onClick(View v)
			{
				daysArray = daysDialog.getCheckedDaysArray();
				if (daysArray.size() > 0)
				{
					daysCheck.setImageResource(R.drawable.check);
					if (trainLinesArray.size() > 0)
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
		Log.w(TAG, "saveState");
		
		/*
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
		calendar.set(Calendar.MINUTE, timePicker.getCurrentMinute());
		 */
		
		int hour = timePicker.getCurrentHour();
		int minute = timePicker.getCurrentMinute();

		Resources res = this.getResources();
		String[] days = res.getStringArray(R.array.days_array);
		List<String> daysPositionArray = new ArrayList<String>();

		for (int h = 0; h < days.length; h++)
		{
			daysPositionArray.add(days[h]);
		}

		// send off to database!

		ReminderManager reminderMgr = new ReminderManager(getApplicationContext());

		// set alerts for each day in the day array
		
		for (int i = 0; i < daysArray.size(); i++)
		{
			reminderMgr.setReminder(hour, minute, daysPositionArray.indexOf(daysArray.get(i)) + 1);
			Log.w(TAG, "day = "+daysArray.get(i) + " day int = "+daysPositionArray.indexOf(daysArray.get(i)) + 1);
		}
	}
}
