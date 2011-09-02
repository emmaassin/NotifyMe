package com.bitty.notifyme;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;

public class ReminderService extends WakeReminderIntentService {

	public ReminderService() {
		super("ReminderService");
			}

	@Override
	void doReminderWork(Intent intent) {
		
		// so here is where the app reaches out to the MTA service to find out if there's currently a delay on the line we care about
		// we can store a reference to the line in the extras
		// once we've grabbed that data, if there's something to report, do the notify code below
		
		int noteId = intent.getExtras().getInt("alarm_id");
		 
		NotificationManager mgr = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
						
		Intent notificationIntent = new Intent(this, DelayInfoActivity.class); 
		
		PendingIntent pi = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_ONE_SHOT);
		
		Notification note = new Notification(android.R.drawable.stat_notify_error, "MTA DELAY!", System.currentTimeMillis());
		note.setLatestEventInfo(this, "MTA DELAY!", "PRESS FOR MORE INFO", pi);
		note.flags |= Notification.FLAG_AUTO_CANCEL; 
		
		mgr.notify(noteId, note);
		
	}
}
