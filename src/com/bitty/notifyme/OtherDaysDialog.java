package com.bitty.notifyme;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

public class OtherDaysDialog extends Dialog {
	
	private TextView dialogText;
    public Button okButton;
    public Button cancelButton;
    private CheckBox sundayBox, mondayBox, tuesdayBox, wednesdayBox, thursdayBox, fridayBox, saturdayBox;
    private List<CheckBox> checkBoxArray = new ArrayList<CheckBox>();
    private List<String> checkedDaysArray = new ArrayList<String>();

    public OtherDaysDialog(Context context)
    {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.other_days_popup);
        
        okButton = (Button) findViewById(R.id.ok_button);
        cancelButton = (Button) findViewById(R.id.cancel_button);
        dialogText = (TextView) findViewById(R.id.other_days_text);
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
        
        for(int i = 0; i < checkBoxArray.size(); i++)
        {
        	checkBoxArray.get(i).setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					if(isChecked)
					{
						checkedDaysArray.add((String) buttonView.getText());
					} else {
						checkedDaysArray.remove((String) buttonView.getText());
					}
				}
			});
        }
    }
    
    public ArrayList getCheckedDaysArray()
    {
		return (ArrayList) checkedDaysArray;
    }

    public void setAlreadyChecked(List<String> daysArray) {
		for(int i = 0; i < checkBoxArray.size(); i++)
        {
			if(daysArray.contains(checkBoxArray.get(i).getText()))
			{
				checkBoxArray.get(i).setChecked(true);
			}
        }
		Toast.makeText(getContext(), checkedDaysArray.toString(), Toast.LENGTH_SHORT).show();
	}
}
