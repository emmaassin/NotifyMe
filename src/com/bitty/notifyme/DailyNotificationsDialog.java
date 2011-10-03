package com.bitty.notifyme;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DailyNotificationsDialog extends Dialog
{
	private static final String TAG = "NotifyInfoDialog";

	private TextView dayText;
	private Button returnHomeButton;
	private Context mContext;

	private LinearLayout notificationList;

	public DailyNotificationsDialog(Context context)
	{
		
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

		// set all the data from the database into this list]

		returnHomeButton.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				cancel();
			}
		});
	}

	public void passInData(String dayName, ArrayList<NotifyMeItem> notifyItemByDay)
	{
		// and pass in an array of all the notifications for that day?
		dayText.setText(dayName);
		// for how ever many notifications. and correct stuff to pass in.

		/*
		for (int j = 0; j < notifyItemByDay.size(); j++)
		{
			String whatDay = Integer.toString(notifyItemByDay.get(j).getDay());
			String db_ID = Long.toString(notifyItemByDay.get(j).getDB_ID());
			Log.w(TAG, "day = " + whatDay + "  db_ID = " + db_ID);
			
			List<String> subways = notifyItemByDay.get(j).getSubways();
			for(int k = 0; k < subways.size(); k++){
				Log.w(TAG, "subway for this day = " + subways.get(k));
			}
		}
		 */
		
		//this needs to be modified to a ListView with an ArrayAdapter
		for (int i = 0; i < 3; i++)
		{
			DailyNotificationsItem item = new DailyNotificationsItem(mContext);
			//item.setContent(10, 40, "123");
			notificationList.addView(item);
		}
	}
}
