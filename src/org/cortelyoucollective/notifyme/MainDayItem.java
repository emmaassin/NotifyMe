package org.cortelyoucollective.notifyme;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import org.cortelyoucollective.notifyme.R;

public class MainDayItem extends FrameLayout
{
	private String dayName; //name of day e.g. "MONDAY", "TUESDAY" etc
	public int notifyCount; //number of notifications for the day
	public int dayDBValue; //DB value used to pull relevant data for particular day

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

	public void init(String _dayName, int _dayDBValue, int _notifyCount)
	{
		dayName = _dayName;
		dayDBValue = _dayDBValue;
		notifyCount = _notifyCount;
		
		boxText.setText(_dayName);
		
		displaySetNotifications();
	}

	public String getDayName()
	{
		return dayName;
	}
	
	public int getDayID(){
		return dayDBValue;
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