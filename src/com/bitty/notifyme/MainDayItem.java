package com.bitty.notifyme;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainDayItem extends FrameLayout
{
	public int notifyCount;
	public int dayIntValue;
	public String dayName;

	private TextView boxText;
	private TextView howMany;
	private View line;
	private LinearLayout notificationDisplay;
	private FrameLayout box;

	public MainDayItem(Context context)
	{
		super(context);
		LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		layoutInflater.inflate(R.layout.main_day_item, this);
		
		boxText = (TextView) findViewById(R.id.boxtext);
		Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/VarelaRound-Regular.ttf");
		boxText.setTypeface(font);
		line = (View) findViewById(R.id.thin_line);
		howMany = (TextView) findViewById(R.id.how_many);
		Typeface font2 = Typeface.createFromAsset(context.getAssets(), "fonts/DINEngschrift-Regular.ttf");
		howMany.setTypeface(font2);
		notificationDisplay = (LinearLayout) findViewById(R.id.notification_display);
		box = (FrameLayout) findViewById(R.id.box);
	}

	public void init(String _dayName, int _dayIntValue, int _notifyCount)
	{
		dayName = _dayName;
		dayIntValue = _dayIntValue;
		notifyCount = _notifyCount;
		
		boxText.setText(_dayName);
		
		// for now... but we need to grab this from the database
		displaySetNotifications();
	}

	public void displaySetNotifications()
	{
		if (notifyCount > 0)
		{
			someNotifications();
		} else
		{
			noNotifications();
		}
	}

	private void someNotifications()
	{
		howMany.setText(String.valueOf(notifyCount));
		notificationDisplay.setVisibility(VISIBLE);
		box.setBackgroundColor(0xffececec);
	}

	private void noNotifications()
	{
		notificationDisplay.setVisibility(GONE);
		box.setBackgroundColor(0xffffffff);
	}
}