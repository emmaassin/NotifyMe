package org.cortelyoucollective.notifyme;

import java.util.ArrayList;
import org.cortelyoucollective.notifyme.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class MTACurrentStatusActivity extends Activity
{
	private static final String TAG = "CurrentStatusActivity";
	static final private int STATUS_DIALOG = 1;

	private ListView statusListView;
	private MTAStatusAdapter aa;
	private ArrayList<MTAStatusItem> statusArray;
	private MTAStatusItem selectedStatusItem;

	private Button homeButton;

	@Override
	public void onCreate(Bundle icicle)
	{
		super.onCreate(icicle);
		setContentView(R.layout.status_main);

		Typeface font = Typeface.createFromAsset(getAssets(), "fonts/VarelaRound-Regular.ttf");
		Typeface font2 = Typeface.createFromAsset(getAssets(), "fonts/DINEngschrift-Regular.ttf");

		TextView title = (TextView) findViewById(R.id.title);
		title.setTypeface(font);

		TextView infoHeader = (TextView) findViewById(R.id.info_header);
		infoHeader.setTypeface(font);

		TextView infoBody = (TextView) findViewById(R.id.info_body);
		infoBody.setTypeface(font);

		final Context context = this;

		statusListView = (ListView) this.findViewById(R.id.statusListView);
		statusListView.setOnItemClickListener(new OnItemClickListener() {
			@SuppressWarnings("unchecked")
			public void onItemClick(AdapterView _av, View _v, int _index, long arg3) 
			{
				selectedStatusItem = statusArray.get(_index);

				// CALL MTA INFO ACTIVITY
				Intent intent = new Intent(context, MTAInfoActivity.class);
				intent.putExtra("line", selectedStatusItem.getLine());
				intent.putExtra("status_txt", selectedStatusItem.getStatusText());
				startActivity(intent);
			}
		});

		homeButton = (Button) findViewById(R.id.home_btn);
		homeButton.setTypeface(font2);
		homeButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v)
			{
				finish();
				Intent myIntent = new Intent(getApplicationContext(),MainActivity.class);
				startActivityForResult(myIntent, 0);
			}
		});

		statusArray = ((NotifyApplication) getApplication()).getMTAStatusArray();

		int layoutID = R.layout.listviewitem;
		aa = new MTAStatusAdapter(this, layoutID, statusArray);
		statusListView.setAdapter(aa);
	}

	@Override
	protected void onStart()
	{
		// Log.w(TAG, "onStart");
		super.onStart();
	}

	@Override
	protected void onResume()
	{
		// Log.w(TAG, "onResume");
		super.onResume();
	}

	@Override
	protected void onPause()
	{
		// Log.w(TAG, "onPause");
		super.onPause();
	}

	@Override
	protected void onStop()
	{
		// Log.w(TAG, "onStop");
		super.onStop();
	}

	@Override
	protected void onDestroy()
	{
		// Log.w(TAG, "onDestroy");
		super.onDestroy();
	}
}
