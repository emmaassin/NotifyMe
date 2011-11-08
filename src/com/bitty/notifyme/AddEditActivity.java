package com.bitty.notifyme;

import java.util.ArrayList;
import java.util.List;

import com.bitty.utils.Convert;

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

	private TextView title, trainsText, daysText, time_title;
	private LinearLayout subwayButton, daysButton;
	private TimeChooser timePicker;
	private Button saveButton, cancelButton;
	private CheckboxDialog daysDialog;
	private SelectSubwayDialog subwayDialog;
	private CheckboxDialog trainsDialog;
	private ImageView trainsCheck, daysCheck;

	public ArrayList<String> trainsSelected = new ArrayList<String>();
	public List<Integer> daysSelectedArr = new ArrayList<Integer>();

	private NotifyDBAdapter notifyDB;
	private NotifyMeItem notifyEditItem;

	private Boolean isEditMode;
	
	private ReminderManager reminderMngr;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editscreen);
		
		reminderMngr = new ReminderManager(this);
		
		notifyDB = ((NotifyApplication) getApplication()).getNotifyDB();

		title = (TextView) findViewById(R.id.edit_title1);
		Typeface font = Typeface.createFromAsset(this.getAssets(), "fonts/VarelaRound-Regular.ttf");
		title.setTypeface(font);
		trainsText = (TextView) findViewById(R.id.trains_text);
		daysText = (TextView) findViewById(R.id.days_text);
		time_title = (TextView) findViewById(R.id.edit_title2);
		Typeface font2 = Typeface.createFromAsset(this.getAssets(), "fonts/DINEngschrift-Regular.ttf");
		trainsText.setTypeface(font2);
		daysText.setTypeface(font2);
		time_title.setTypeface(font2);

		trainsCheck = (ImageView) findViewById(R.id.trains_check);
		daysCheck = (ImageView) findViewById(R.id.days_check);

		timePicker = (TimeChooser) findViewById(R.id.time_picker);
		timePicker.initComponent(this);

		saveButton = (Button) findViewById(R.id.save_button);
		saveButton.setTypeface(font2);
		cancelButton = (Button) findViewById(R.id.cancel_button);
		cancelButton.setTypeface(font2);
		subwayButton = (LinearLayout) findViewById(R.id.trains);
		daysButton = (LinearLayout) findViewById(R.id.days);

		// Check to if this is an edit

		isEditMode = getIntent().getBooleanExtra("edit_mode", false);

		if (isEditMode)
		{
			int array_index = getIntent().getIntExtra("array_index", -1);
			NotifyApplication app = (NotifyApplication) getApplication();

			notifyEditItem = (NotifyMeItem) app.getDailyNotificationArray().get(array_index);
			daysSelectedArr.add(notifyEditItem.getDay());
			
			
			trainsSelected = (ArrayList<String>) notifyEditItem.getSubways();
			// hiding the choose days button so it can't be edited
			daysButton.setVisibility(View.GONE);
			//LinearLayout thinLine = (LinearLayout) findViewById(R.id.thinline);
			//thinLine.setVisibility(View.GONE);

			//Log.w(TAG, "HOUR : " + Integer.toString(notifyEditItem.getHour()));
			timePicker.setCurrentHour(notifyEditItem.getHour());
			timePicker.setCurrentMinute(notifyEditItem.getMinutes());
			
			title.setText(R.string.add_notification_title);
		}

		saveButton.setEnabled(true);
		saveButton.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View view)
			{
				if (trainsSelected.size() < 1)
				{
					Toast.makeText(AddEditActivity.this, getString(R.string.no_lines_selected_message),
							Toast.LENGTH_SHORT).show();
				} else
				{
					if (daysSelectedArr.size() < 1)
					{
						Toast.makeText(AddEditActivity.this, getString(R.string.no_days_selected_message),
								Toast.LENGTH_SHORT).show();
					} else
					{
						String msg;
						if(isEditMode){
							saveEditItem();
							msg = getString(R.string.notification_edit_message);
						}else{
							saveState();
							msg = getString(R.string.notification_saved_message);
						}
						setResult(RESULT_OK);
						Toast.makeText(AddEditActivity.this, msg, Toast.LENGTH_SHORT).show();
					}
				}
			}
		});

		cancelButton.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View view)
			{
				String msg;
				if(isEditMode)
					msg = getString(R.string.notification_cancel_edit);
				else
					msg = getString(R.string.notification_cancel_add);
				
				Toast.makeText(AddEditActivity.this, msg, Toast.LENGTH_SHORT).show();
				finish();
			}
		});

		daysButton.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v){ createDaysPopup(); }
		});

		subwayButton.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v){
				NotifyApplication app = (NotifyApplication) getApplication();
				if(app.getCurrentTrainType() == "subway")
				{
					createSubwayPopUp(); 
				} else if (app.getCurrentTrainType() == "LIRR"){
					createTrainPopup();
				} else if (app.getCurrentTrainType() == "MN") {
					createTrainPopup();
				}
			}
		});
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
		//Log.w(TAG, "onResume");
		super.onResume();
		
		if(isEditMode){
			title.setText(R.string.edit_notification_title);
			trainsText.setText(R.string.edit_line);
			daysText.setText(R.string.edit_days);
			time_title.setText(R.string.edit_time);
		}else{
			title.setText(R.string.add_notification_title);
			trainsText.setText(R.string.choose_line);
			daysText.setText(R.string.choose_days);
			time_title.setText(R.string.choose_time_title);
		}
	}

	@Override
	protected void onPause()
	{
		// Log.w(TAG, "onPause");
		super.onPause();
	}

	@Override
	protected void onStop()
	{
		// Log.w(TAG, "onStop");
		super.onStop();
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
	}

	private void createSubwayPopUp()
	{
		subwayDialog = new SelectSubwayDialog(this);
		if (trainsSelected.size() > 0)
			subwayDialog.setAlreadyChecked(trainsSelected);

		subwayDialog.show();
		subwayDialog.saveButton.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				trainsSelected = subwayDialog.getCheckedLinesArray();
				if (trainsSelected.size() > 0)
				{
					trainsCheck.setImageResource(R.drawable.check);
				} else
				{
					trainsCheck.setImageResource(R.drawable.add);
				}
				subwayDialog.cancel();
			}
		});
	}
	
	protected void createTrainPopup()
	{
		int trainArray = 0;
		NotifyApplication app = (NotifyApplication) getApplication();
		if(app.getCurrentTrainType() == "LIRR")
		{
			trainArray = R.array.LIRR_array;

		} else if (app.getCurrentTrainType() == "MN")
		{
			trainArray = R.array.MN_array;
		}
		trainsDialog = new CheckboxDialog(this);
		trainsDialog.init(trainArray, true);
		if (trainsSelected.size() > 0)
			trainsDialog.setAlreadyCheckedTrains(trainsSelected);
		
		trainsDialog.show();
		trainsDialog.saveButton.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				trainsSelected = trainsDialog.getCheckedLinesArray();
				if (trainsSelected.size() > 0)
				{
					trainsCheck.setImageResource(R.drawable.check);
				} else
				{
					trainsCheck.setImageResource(R.drawable.add);
				}
				trainsDialog.cancel();
			}
		});
	}

	private void createDaysPopup()
	{
		daysDialog = new CheckboxDialog(this);
		daysDialog.init(R.array.days_array, false);

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
		// Log.w(TAG, "saveState");
		int hour = timePicker.getCurrentHour();
		int minute = timePicker.getCurrentMinute();

		// set alerts for each day in the day array
		for (int i = 0; i < daysSelectedArr.size(); i++)
		{
			int daySelected = 0;
			// insert to database and serialize subway lines array
			if(daysSelectedArr.get(i) < 6)
			{
				daySelected = daysSelectedArr.get(i) + 2;
			} else {
				daySelected = 1;
			}
			//int daySelected = daysSelectedArr.get(i);
			NotifyMeItem item = new NotifyMeItem(trainsSelected, daySelected, hour, minute);
			long alarmID = notifyDB.insertNotification(item);

			reminderMngr.setReminder(hour, minute, daySelected, alarmID);
		}
		finish();
	}
	
	private void saveEditItem()
	{
		notifyEditItem.setHour(timePicker.getCurrentHour());
		notifyEditItem.setMinutes(timePicker.getCurrentMinute());
		notifyEditItem.setDay(daysSelectedArr.get(0));
		notifyEditItem.setSubways(trainsSelected);
		notifyDB.updateNotification(notifyEditItem);
		reminderMngr.clearReminder(Convert.safeLongToInt(notifyEditItem.getDB_ID()));
		reminderMngr.setReminder(notifyEditItem.getHour(), notifyEditItem.getMinutes(), notifyEditItem.getDay(), notifyEditItem.getDB_ID());
		finish();
	}
}
