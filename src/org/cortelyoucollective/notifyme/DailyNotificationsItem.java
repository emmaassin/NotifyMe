package org.cortelyoucollective.notifyme;

import java.util.List;
import org.cortelyoucollective.notifyme.R;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class DailyNotificationsItem extends RelativeLayout
{
	private static final String TAG = "DailyNotificationsItem";
	public static final String DELETE_ITEM = "DELETE_ITEM";

	private Context mContext;
	private LinearLayout linesImagesHolder;
	private TextView timeTextView;
	private Button deleteButton;
	private Button editButton;

	private long db_ID;
	private int arrayIndex;

	public DailyNotificationsItem(Context context)
	{
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
			public void onClick(View v)
			{
				AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
				builder.setMessage(R.string.delete_notification_message).setTitle(R.string.delete_notification_title)
						.setCancelable(false).setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which)
							{
								// delete this notification!
								Intent intent = new Intent(DELETE_ITEM);
								intent.putExtra("array_index", arrayIndex);
								intent.putExtra("db_ID", db_ID);
								mContext.sendBroadcast(intent);
							}
						}).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which)
							{
								dialog.cancel();
							}
						});
				builder.create().show();
			}
		});

	}

	public void setDB_ID(long _id)
	{
		db_ID = _id;
	}

	/*
	 * Array index used to get NotifyArrayItems
	 */
	public void setArrayIndex(int _index)
	{
		arrayIndex = _index;
	}

	public void setContent(int hr, int min, List<String> trains, final String trainType)
	{
		//Log.w(TAG, "type = " + trainType);
		editButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v)
			{
				// edit this notification
				Intent i = new Intent(mContext, AddEditActivity.class);
				i.putExtra("array_index", arrayIndex);
				i.putExtra("edit_mode", true);
				i.putExtra("train_type", trainType);
				// ////////////!!!!!!!!!!!!!!!!!????????????///////////////////////
				// need to get the type from the database and replace "subway"
				// above
				mContext.startActivity(i);
			}
		});

		LinearLayout currentRow = null;

		for (int i = 0; i < trains.size(); i++)
		{
			if (trainType.equals("subway"))
			{
				ImageView img = new ImageView(mContext);
				String resID = "line_" + trains.get(i).toLowerCase();
				int resources = getResources().getIdentifier(resID, "drawable", "org.cortelyoucollective.notifyme");

				// load the original BitMap
				Bitmap bitmapOrg = BitmapFactory.decodeResource(getResources(), resources);

				int width = bitmapOrg.getWidth();
				int height = bitmapOrg.getHeight();
				int newHeight = 30;

				// calculate the scale
				float scale = ((float) newHeight) / height;

				// create a matrix for the manipulation
				Matrix matrix = new Matrix();
				// resize the bit map
				matrix.postScale(scale, scale);

				// recreate the new Bitmap
				Bitmap resizedBitmap = Bitmap.createBitmap(bitmapOrg, 0, 0, width, height, matrix, true);

				// make a Drawable from Bitmap to allow to set the BitMap
				// to the ImageView
				BitmapDrawable bmd = new BitmapDrawable(resizedBitmap);

				// set the Drawable on the ImageView
				img.setImageDrawable(bmd);

				// center the Image
				img.setScaleType(ScaleType.CENTER);

				if (i % 3 == 0)
				{
					LinearLayout ll = new LinearLayout(mContext);
					linesImagesHolder.addView(ll);
					currentRow = ll;
				}

				currentRow.addView(img);
			}
			else
			{
				TextView line = new TextView(mContext);
				Typeface font = Typeface.createFromAsset(mContext.getAssets(), "fonts/VarelaRound-Regular.ttf");
				line.setTypeface(font);
				line.setTextColor(R.color.black);
				line.setText(trains.get(i));
				
				linesImagesHolder.addView(line);
			}
		}

		String minString;

		if (min < 10)
		{
			minString = "0" + min;
		} else {
			minString = String.valueOf(min);
		}
			
		//Toast.makeText(mContext, String.valueOf(hr), Toast.LENGTH_LONG).show();
		
		if (hr < 12)
		{
			timeTextView.setText(hr + ":" + minString + " AM");
		} else if (hr == 12)
		{
			timeTextView.setText(12 + ":" + minString + " PM");
		} else {
			timeTextView.setText((hr - 12) + ":" + minString + " PM");
		}
			
			
	}

}
