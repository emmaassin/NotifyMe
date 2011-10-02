package com.bitty.notifyme;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class OnBootReceiver extends BroadcastReceiver
{

	@Override
	public void onReceive(Context context, Intent intent) {
		// get all notifications from the database and use their data to re-set alarms!
		
		Toast.makeText(context, "The Boot Receiver was received!", Toast.LENGTH_LONG).show();
	}

}
