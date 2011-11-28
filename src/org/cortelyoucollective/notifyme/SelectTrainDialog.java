package org.cortelyoucollective.notifyme;

import java.util.ArrayList;
import java.util.List;
import org.cortelyoucollective.notifyme.R;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class SelectTrainDialog extends CheckboxDialog
{
	private static final String TAG = "SelectTrainDialog";
	private List<String> selectedTrains = new ArrayList<String>();
	
	public SelectTrainDialog(Context context)
	{
		super(context);
		dialogText.setText(R.string.choose_line);
	}
	
	public void init(int resourceID)
	{
		Context context = getContext();
		String[] trainLabels = context.getResources().getStringArray(resourceID);

		for (int i = 0; i < trainLabels.length; i++)
		{
			Log.w(TAG, trainLabels[i]);
			
			LinearLayout ll = new LinearLayout(context);
			ll.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, 1));
			ll.setBackgroundColor(Color.parseColor("#e3e3e3"));

			CheckBox cb = new CheckBox(context);
			cb.setTypeface(font);
			cb.setText(trainLabels[i]);
			cb.setTag(trainLabels[i]);
			cb.setHeight(47);
			cb.setTextColor(Color.parseColor("#3e3e3e"));
			cb.setButtonDrawable(R.drawable.check_box);
			cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
				{
					if (isChecked)
					{
						selectedTrains.add((String) buttonView.getTag());
					} else
					{
						String tag = (String) buttonView.getTag();
						selectedTrains.remove(new String(tag));
					}
				}
			});
			
			checkboxContainer.addView(ll);
			checkboxContainer.addView(cb);
			checkBoxArray.add(cb);
		}
	}

	public ArrayList getDataArray()
	{
		return (ArrayList) selectedTrains;
	}
	
}
