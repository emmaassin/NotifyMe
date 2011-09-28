package com.bitty.notifyme;

import java.util.Calendar;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.SystemClock;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class NotifyInfoDialog extends Dialog{

	private TextView dayText;
	private Button returnHomeButton;
	private Context mContext;
	
	private LinearLayout notificationList;
	
	public NotifyInfoDialog(Context context) {
		super(context, R.style.FullscreenDialogTheme);
		mContext = context;
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.notify_info_dialog);
        
        dayText = (TextView) findViewById(R.id.day_text);
        returnHomeButton = (Button) findViewById(R.id.return_home_btn);
        notificationList = (LinearLayout) findViewById(R.id.notification_list);
        
        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/VarelaRound-Regular.ttf");
        Typeface font2 = Typeface.createFromAsset(context.getAssets(), "fonts/DINEngschrift-Regular.ttf");
        dayText.setTypeface(font);
        returnHomeButton.setTypeface(font2);
        
        //set all the data from the database into this list]
       
        returnHomeButton.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				cancel();
			}
		});
	}
	
	public void passInData(String dayOfWeek)
	{
		// and pass in an array of all the notifications for that day?
		dayText.setText(dayOfWeek);
		// for how ever many notifications.  and correct stuff to pass in.
		for(int i = 0; i<3; i++)
		{
			NotificationListItem item = new NotificationListItem(mContext);
			item.setContent(10, 40, "123");
			notificationList.addView(item);
		}
	}
}
