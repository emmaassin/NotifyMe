package com.bitty.notifyme;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import android.os.Bundle;

public class AlarmReceiver extends BroadcastReceiver {
	
	
 @Override
 public void onReceive(Context context, Intent intent) {
   try {
	   Bundle extras = intent.getExtras();
	   int id = extras.getInt("alarm_id");
     
	   Intent i = new Intent(context, ReminderService.class);
	   i.putExtra("alarm_id", id);
	   context.startService(i);
    } catch (Exception e) {
    	Toast.makeText(context, "There was an error somewhere, but we still received an alarm", Toast.LENGTH_SHORT).show();
    	e.printStackTrace();
    }
 }

}

