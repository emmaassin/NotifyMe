package com.bitty.notifyme;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class TrainLinesDialog extends Dialog{

	private TextView dialogText;
	private LinearLayout scrollContents;
    public Button okButton;
    public Button cancelButton;
    private List<CheckBox> checkBoxArray = new ArrayList<CheckBox>();
    private List<String> checkedLinesArray = new ArrayList<String>();
	
	public TrainLinesDialog(Context context)
    {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.train_lines_popup);
        
        okButton = (Button) findViewById(R.id.ok_button);
        cancelButton = (Button) findViewById(R.id.cancel_button);
        dialogText = (TextView) findViewById(R.id.other_days_text);
        scrollContents = (LinearLayout) findViewById(R.id.scrollcontents);
        
        Resources res = context.getResources();
        String[] trainLines = res.getStringArray(R.array.trains_array);
        
        for(int i = 0; i < trainLines.length; i++)
        {
        	CheckBox cb = new CheckBox(context);
        	cb.setText(trainLines[i]);
        	scrollContents.addView(cb);
        	checkBoxArray.add(cb);
        	cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					if(isChecked)
					{
						checkedLinesArray.add((String) buttonView.getText());
					} else {
						checkedLinesArray.remove((String) buttonView.getText());
					}
				}
			});
        }
    }
	
	public ArrayList getCheckedLinessArray()
    {
		return (ArrayList) checkedLinesArray;
    }

	public void setAlreadyChecked(List<String> trainLinesArray) {
		for(int i = 0; i < checkBoxArray.size(); i++)
        {
			if(trainLinesArray.contains(checkBoxArray.get(i).getText()))
			{
				checkBoxArray.get(i).setChecked(true);
			}
        }
		
	}

}
