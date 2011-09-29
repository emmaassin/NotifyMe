package com.bitty.notifyme;

import java.util.Calendar;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class NotificationListItem extends RelativeLayout
{
	private Context mContext;
	private LinearLayout linesImagesHolder;
	private TextView timeTextView;
	private Button deleteButton;
	private Button editButton;
	
	public NotificationListItem(Context context) {
		super(context);
		mContext = context;
		LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		layoutInflater.inflate(R.layout.notification_list_item, this);
		
		linesImagesHolder = (LinearLayout) findViewById(R.id.train_lines_holder);
		timeTextView = (TextView) findViewById(R.id.time_text_view);
		deleteButton = (Button) findViewById(R.id.delete_button);
		editButton = (Button) findViewById(R.id.edit_button);
		
		Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/VarelaRound-Regular.ttf");
		timeTextView.setTypeface(font);
		
		deleteButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				
			}
		});
		
		editButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				
			}
		});
	}

	public void setContent(int hr, int min, String string) {
		// for now
		ImageView img = new ImageView(mContext);
		img.setImageResource(R.drawable.line_123);
		linesImagesHolder.addView(img);
		
		if(hr < 12)
		{
			timeTextView.setText(hr + ":" + min + " AM");
		} else if (hr == 12)
		{
			timeTextView.setText(12 + ":" + min + " AM");
		} else {
			timeTextView.setText((hr - 12) + ":" + min + " PM");
		}
		
	} 

}
