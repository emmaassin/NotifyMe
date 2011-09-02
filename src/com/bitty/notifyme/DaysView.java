package com.bitty.notifyme;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class DaysView extends Activity {
	
	private static final int ACTIVITY_CREATE=0;
    private static final int ACTIVITY_EDIT=1;
    
    private TextView titleText;
	private DayBox mondayBox, tuesdayBox, wednesdayBox, thursdayBox, fridayBox, saturdayBox, sundayBox;
    private DayBox[] dayBoxArray = {mondayBox, tuesdayBox, wednesdayBox, thursdayBox, fridayBox, saturdayBox, sundayBox};
    private List<String> dayOfWeekArray = new ArrayList<String>();
    private LinearLayout holder;
    private LinearLayout addNotificationButton;
    private TextView addText;
    private NotificationChooser notificationChooser;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        titleText = (TextView) findViewById(R.id.title);
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/VarelaRound-Regular.ttf");
        titleText.setTypeface(font);
        
        Resources res = this.getResources();
	    String[] days = res.getStringArray(R.array.days_array);
	     
	    for(int i=1; i<days.length; i++)
	    {
	    	dayOfWeekArray.add(days[i]);
	    }
	    
	    dayOfWeekArray.add(days[0]);
        
        holder = (LinearLayout) findViewById(R.id.days_holder);
        
        for(int i = 0; i < dayBoxArray.length; i++)
		{
        	dayBoxArray[i] = new DayBox(this);
        	dayBoxArray[i].init(dayOfWeekArray.get(i).toUpperCase());
			
        	holder.addView(dayBoxArray[i]);
        	
        	dayBoxArray[i].setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					handleDayBoxClick((DayBox) arg0);
				}
			});
		}
        
        addNotificationButton = (LinearLayout) findViewById(R.id.add);
        addText = (TextView) findViewById(R.id.add_text);
        Typeface font2 = Typeface.createFromAsset(getAssets(), "fonts/DINEngschrift-Regular.ttf");
        addText.setTypeface(font2);
        
        addNotificationButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				createReminder();
			}
		});
    }
    
    private void handleDayBoxClick(DayBox daybox)
    {
		if(daybox.notifications > 0)
		{
			notificationChooser = new NotificationChooser(this);
			notificationChooser.show();
			notificationChooser.cancelButton.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					notificationChooser.dismiss();
				}
			});
		}
	}
    
    private void createReminder() {
        Intent i = new Intent(this, NotifyEditActivity.class);
        startActivityForResult(i, ACTIVITY_CREATE);
    }
}