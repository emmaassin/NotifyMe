package org.cortelyoucollective.notifyme;

import java.util.ArrayList;
import java.util.List;
import org.cortelyoucollective.notifyme.R;

import android.content.Context;
import android.graphics.Color;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class SelectDayDialog extends CheckboxDialog
{
	private static final String TAG = "SelectDayDialog";
	private List<Integer> selectedDays = new ArrayList<Integer>();

	public SelectDayDialog(Context context)
	{
		super(context);
		dialogText.setText(R.string.choose_days2);
	}

	public void init(int resourceID)
	{
		Context context = getContext();
		String[] dayLabels = context.getResources().getStringArray(resourceID);

		for (int i = 0; i < dayLabels.length; i++)
		{
			LinearLayout ll = new LinearLayout(context);
			ll.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, 1));
			ll.setBackgroundColor(Color.parseColor("#e3e3e3"));

			CheckBox cb = new CheckBox(context);
			cb.setTypeface(font);
			cb.setText(dayLabels[i]);
			cb.setTag(i);
			cb.setHeight(47);
			cb.setTextColor(Color.parseColor("#3e3e3e"));
			cb.setButtonDrawable(R.drawable.check_box);
			cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
				{
					if (isChecked)
					{
						selectedDays.add((Integer) buttonView.getTag());
					} else
					{
						int tag = (Integer) buttonView.getTag();
						selectedDays.remove(new Integer(tag));
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
		return (ArrayList) selectedDays;
	}
}
