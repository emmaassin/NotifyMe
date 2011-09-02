package com.bitty.notifyme;

import java.util.Calendar;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ReminderManager {

	private Context mContext;
	private AlarmManager mAlarmManager;
	
	public ReminderManager(Context context)
	{
		mContext = context;
		mAlarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
	}
	
	public void setReminder(int hr, int min, int day)
	{
		 Intent intent = new Intent(mContext, AlarmReceiver.class);
		 
		 int alarmId = 0;
		 // alarm Id needs to be a reference to the database line for this notification
		 intent.putExtra("alarm_id", alarmId);
		 
		 PendingIntent sender = PendingIntent.getBroadcast(mContext, alarmId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		 
		 Calendar calendar = Calendar.getInstance();
		 calendar.set(Calendar.HOUR_OF_DAY, hr);
		 calendar.set(Calendar.MINUTE, min);
		 calendar.set(Calendar.SECOND, 0);
		 calendar.setFirstDayOfWeek(Calendar.SUNDAY);
		 
		 if(calendar.get(Calendar.DAY_OF_WEEK) != day)
		 {
			 if(day > calendar.get(Calendar.DAY_OF_WEEK))
			 {
				 calendar.add(Calendar.DAY_OF_MONTH, day - calendar.get(Calendar.DAY_OF_WEEK));
			 }
			 
			 if(day < calendar.get(Calendar.DAY_OF_WEEK))
			 {
				 calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
				 if(day > 1)
				 {
					 calendar.add(Calendar.DAY_OF_MONTH, 7 - (calendar.get(Calendar.DAY_OF_WEEK) - day));
				 }
			 }
		 }
		 
		 //Toast.makeText(mContext, ""+calendar.get(Calendar.DAY_OF_WEEK), Toast.LENGTH_LONG).show();
		 
		 long start = calendar.getTimeInMillis();
		 if (calendar.before(Calendar.getInstance())) {
		      start += AlarmManager.INTERVAL_DAY * 7;
		 }
		 Log.i("LOGGING",calendar.toString());
		 // set the alarm to repeat every week at the same time
		 mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, start, AlarmManager.INTERVAL_DAY * 7, sender);
	}
	
	public void clearReminder(int theId)
	{
		 Intent intent = new Intent(mContext, AlarmReceiver.class);
		 int alarmId = 0;
		 intent.putExtra("alarm_id", alarmId);
		 
		 PendingIntent sender = PendingIntent.getBroadcast(mContext, theId, intent, 0);

		 if(sender != null)
		 {
			 mAlarmManager.cancel(sender);
		 }
		 
		 sender.cancel();
	}
	
}
