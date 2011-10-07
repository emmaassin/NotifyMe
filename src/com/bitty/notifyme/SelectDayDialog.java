package com.bitty.notifyme;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

public class SelectDayDialog extends Dialog
{
	
	private static final String TAG = "SelectDayDialog";
	
	private TextView dialogText;
	public Button saveButton, cancelButton;
	private CheckBox sundayBox, mondayBox, tuesdayBox, wednesdayBox, thursdayBox, fridayBox, saturdayBox;
	private List<CheckBox> checkBoxArray = new ArrayList<CheckBox>();
	//private List<String> checkedDaysArray = new ArrayList<String>();
	private List<Integer> checkedDaysArray = new ArrayList<Integer>();

	public SelectDayDialog(Context context)
	{
		super(context, R.style.FullscreenDialogTheme);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.day_select_dialog);

		Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/VarelaRound-Regular.ttf");
		Typeface font2 = Typeface.createFromAsset(context.getAssets(), "fonts/DINEngschrift-Regular.ttf");

		saveButton = (Button) findViewById(R.id.save_button);
		saveButton.setTypeface(font2);
		cancelButton = (Button) findViewById(R.id.cancel_button);
		cancelButton.setTypeface(font2);
		dialogText = (TextView) findViewById(R.id.other_days_text);
		dialogText.setTypeface(font);
		sundayBox = (CheckBox) findViewById(R.id.sundayCheckBox);
		checkBoxArray.add(sundayBox);
		mondayBox = (CheckBox) findViewById(R.id.mondayCheckBox);
		checkBoxArray.add(mondayBox);
		tuesdayBox = (CheckBox) findViewById(R.id.tuesdayCheckBox);
		checkBoxArray.add(tuesdayBox);
		wednesdayBox = (CheckBox) findViewById(R.id.wednesdayCheckBox);
		checkBoxArray.add(wednesdayBox);
		thursdayBox = (CheckBox) findViewById(R.id.thursdayCheckBox);
		checkBoxArray.add(thursdayBox);
		fridayBox = (CheckBox) findViewById(R.id.fridayCheckBox);
		checkBoxArray.add(fridayBox);
		saturdayBox = (CheckBox) findViewById(R.id.saturdayCheckBox);
		checkBoxArray.add(saturdayBox);

		cancelButton.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				cancel();
			}
		});
		
		for (int i = 0; i < checkBoxArray.size(); i++)
		{
			checkBoxArray.get(i).setTypeface(font);
			checkBoxArray.get(i).setOnCheckedChangeListener(new OnCheckedChangeListener()
			{
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
				{
					if (isChecked)
					{
						//checkedDaysArray.add((String) buttonView.getText());
						checkedDaysArray.add(Integer.parseInt((String) buttonView.getTag()));
					} else
					{
						//checkedDaysArray.remove((String) buttonView.getText());
						int tag = Integer.parseInt((String) buttonView.getTag());
						checkedDaysArray.remove(new Integer(tag));
					}
				}
			});
		}
	}

	public ArrayList getCheckedDaysArray()
	{
		return (ArrayList) checkedDaysArray;
	}
	
	public void setAlreadyChecked(List<Integer> daysArray)
	{
		for (int i = 0; i < checkBoxArray.size(); i++)
		{
			if (daysArray.contains(Integer.parseInt((String)checkBoxArray.get(i).getTag())))
			{
				checkBoxArray.get(i).setChecked(true);
			}
		}
	}
}
