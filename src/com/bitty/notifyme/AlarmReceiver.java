package com.bitty.notifyme;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver
{
	private static final String TAG = "AlarmReceiver";

	public void onReceive(Context context, Intent intent)
	{
		Log.w(TAG, "onReceive");
		try
		{
			Bundle extras = intent.getExtras();
			int id = extras.getInt("alarm_id");

			Intent i = new Intent(context, ReminderService.class);
			i.putExtra("alarm_id", id);
			i.putExtra("subway_line", "123");
			context.startService(i);
		} catch (Exception e)
		{
			Toast.makeText(context, "There was an error somewhere, but we still received an alarm", Toast.LENGTH_SHORT)
					.show();
			e.printStackTrace();
		}
	}

}
