package com.bitty.notifyme;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class CheckboxDialog extends Dialog
{

	private TextView dialogText;
	public Button saveButton, cancelButton;
	private LinearLayout checkboxContainer;
	private List<CheckBox> checkBoxArray = new ArrayList<CheckBox>();
	private List<Integer> checkedBoxesArray = new ArrayList<Integer>();
	private Typeface font;
	private Context mContext;
	
	public CheckboxDialog(Context context) {
		super(context, R.style.FullscreenDialogTheme);
		mContext = context;
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

		cancelButton.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				cancel();
			}
		});
	}
	
	public void init(int boxesToAdd, Integer[] tags)
	{
		Resources res = mContext.getResources();
		String[] addingArray = res.getStringArray(boxesToAdd);
		
		for (int i = 0; i < addingArray.length; i++)
		{
			LinearLayout ll = new LinearLayout(mContext);
			ll.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, 1));
			ll.setBackgroundColor(Color.parseColor("#e3e3e3"));
			checkboxContainer.addView(ll);
			CheckBox cb = new CheckBox(mContext);
			checkboxContainer.addView(cb);
			cb.setTypeface(font);
			checkBoxArray.add(cb);
			cb.setText(addingArray[i]);
			cb.setTag(tags[i]);
			cb.setHeight(47);
			cb.setTextColor(Color.parseColor("#3e3e3e"));
			cb.setButtonDrawable(R.drawable.check_box);
			
			cb.setOnCheckedChangeListener(new OnCheckedChangeListener()
			{
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
				{
					if (isChecked)
					{
						checkedBoxesArray.add((Integer) buttonView.getTag());
					} else
					{
						int tag = (Integer) buttonView.getTag();
						checkedBoxesArray.remove(new Integer(tag));
					}
				}
			});
		}
	}

	public ArrayList getCheckedDaysArray()
	{
		return (ArrayList) checkedBoxesArray;
	}
	
	public void setAlreadyChecked(List<Integer> checkedArray)
	{
		for (int i = 0; i < checkBoxArray.size(); i++)
		{
			if (checkedArray.contains(checkBoxArray.get(i).getTag()))
			{
				checkBoxArray.get(i).setChecked(true);
			}
		}
	}

}
