package com.bitty.notifyme;

import java.util.ArrayList;
import android.app.ListActivity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class DailyNotificationsActivity extends ListActivity{

	private TextView dayText;
	private Button returnHomeButton;
	private Context mContext;
	private ArrayList<NotifyMeItem> notifyMeItem;
	private ArrayList<DailyNotificationsItem> itemArray = new ArrayList<DailyNotificationsItem>();
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.daily_notifications);
		
		dayText = (TextView) findViewById(R.id.day_text);
		returnHomeButton = (Button) findViewById(R.id.return_home_btn);
		
		Typeface font = Typeface.createFromAsset(this.getAssets(), "fonts/VarelaRound-Regular.ttf");
		Typeface font2 = Typeface.createFromAsset(this.getAssets(), "fonts/DINEngschrift-Regular.ttf");
		dayText.setTypeface(font);
		returnHomeButton.setTypeface(font2);
		
		returnHomeButton.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				finish();
			}
		});
		
		notifyMeItem = ((NotifyMeApplication)getApplication()).getCurrentDaysNotifications();
		
		this.setListAdapter(new NotificationListAdapter(this));
		
		dayText.setText(((NotifyMeApplication)getApplication()).getCurrentDay());
	}

	public class NotificationListAdapter extends BaseAdapter {

		public NotificationListAdapter(Context c) {
            mContext = c;
        }
		
		public int getCount() {
			return notifyMeItem.size();
		}

		public Object getItem(int position) {
			return itemArray.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			DailyNotificationsItem item = new DailyNotificationsItem(mContext);
			item.setContent(((NotifyMeItem) notifyMeItem.get(position)).getHour(), ((NotifyMeItem) notifyMeItem.get(position)).getMinutes(), ((NotifyMeItem) notifyMeItem.get(position)).getSubways());
			itemArray.add(item);
        	
        	return item;
		}
		
	}
	
}
