package com.bitty.notifyme;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CheckboxDialog extends Dialog
{
	protected TextView dialogText;
	protected Button saveButton, cancelButton;
	protected LinearLayout checkboxContainer;
	protected List<CheckBox> checkBoxArray = new ArrayList<CheckBox>();

	protected Typeface font;

	public CheckboxDialog(Context context)
	{
		super(context, R.style.FullscreenDialogTheme);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.checkbox_dialog);

		font = Typeface.createFromAsset(context.getAssets(), "fonts/VarelaRound-Regular.ttf");
		Typeface font2 = Typeface.createFromAsset(context.getAssets(), "fonts/DINEngschrift-Regular.ttf");

		saveButton = (Button) findViewById(R.id.save_button);
		saveButton.setTypeface(font2);
		
		cancelButton = (Button) findViewById(R.id.cancel_button);
		cancelButton.setTypeface(font2);
		
		dialogText = (TextView) findViewById(R.id.other_days_text);
		dialogText.setTypeface(font);
		
		checkboxContainer = (LinearLayout) findViewById(R.id.checkbox_container);

		cancelButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v)
			{
				cancel();
			}
		});
	}

	public void init(int resourceID)
	{
	}

	public ArrayList getDataArray()
	{
		return null;
	}

	public void setAlreadyChecked(List<?> checkedArray)
	{
		for (int i = 0; i < checkBoxArray.size(); i++)
		{
			if(checkedArray.contains(checkBoxArray.get(i).getTag()))
				checkBoxArray.get(i).setChecked(true);
		}
	}
}
