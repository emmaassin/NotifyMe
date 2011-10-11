package com.bitty.notifyme;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
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

	@Override
	public void onCreate(Bundle icicle)
	{
		Log.w(TAG, "onCreate");
		super.onCreate(icicle);
		setContentView(R.layout.status_main);
		
		final Context context = this;
		
		statusListView = (ListView) this.findViewById(R.id.statusListView);
		statusListView.setOnItemClickListener(new OnItemClickListener() {
			@SuppressWarnings("unchecked")
			public void onItemClick(AdapterView _av, View _v, int _index, long arg3)
			{
				selectedStatusItem = statusArray.get(_index);
				
				//CALL MTA INFO ACTIVITY
				Intent intent = new Intent(context, MTAInfoActivity.class);
				intent.putExtra("line", selectedStatusItem.getLine());
				intent.putExtra("status_txt", selectedStatusItem.getStatusText());
				startActivity(intent);
			}
		});

		statusArray =  ((NotifyApplication) getApplication()).getMTAStatusArray();
		
		int layoutID = R.layout.listviewitem;
		aa = new MTAStatusAdapter(this, layoutID, statusArray);
		statusListView.setAdapter(aa);
	}

	@Override
	protected void onStart()
	{
		//Log.w(TAG, "onStart");
		super.onStart();
	}

	@Override
	protected void onResume()
	{
		//Log.w(TAG, "onResume");
		super.onResume();
	}

	@Override
	protected void onPause()
	{
		//Log.w(TAG, "onPause");
		super.onPause();
	}

	@Override
	protected void onStop()
	{
		//Log.w(TAG, "onStop");
		super.onStop();
	}

	@Override
	protected void onDestroy()
	{
		//Log.w(TAG, "onDestroy");
		super.onDestroy();
	}
}
