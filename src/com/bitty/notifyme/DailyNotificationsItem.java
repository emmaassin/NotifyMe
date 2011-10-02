package com.bitty.notifyme;

import java.util.Calendar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DailyNotificationsItem extends RelativeLayout
{
	private Context mContext;
	private LinearLayout linesImagesHolder;
	private TextView timeTextView;
	private Button deleteButton;
	private Button editButton;
	
	public DailyNotificationsItem(Context context) {
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
				AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
				builder.setMessage(R.string.are_you_sure_delete)
				.setTitle(R.string.are_you_sure)
				.setCancelable(false)
				.setPositiveButton(R.string.yes,
				new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						// delete this notification!
					}	
				})
				.setNegativeButton(R.string.no,
				new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
				builder.create().show();
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
		ImageView img2 = new ImageView(mContext);
		img2.setImageResource(R.drawable.line_nqr);
		linesImagesHolder.addView(img2);
		
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
