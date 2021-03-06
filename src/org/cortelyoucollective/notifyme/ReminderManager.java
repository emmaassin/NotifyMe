package org.cortelyoucollective.notifyme;

import java.util.Calendar;

import org.cortelyoucollective.utils.Convert;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ReminderManager
{
	private static final String TAG = "ReminderManager";

	private Context context;
	private AlarmManager alarmManager;

	public ReminderManager(Context context)
	{
		this.context = context;
		alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
	}

	public void setReminder(int hour, int minute, int day, long alarmID)
	{
		Log.w(TAG, "setReminder : hour = " + Integer.toString(hour) + "  minute=" + Integer.toString(minute)
				+ " day=" + Integer.toString(day) + " alarmID=" + Long.toString(alarmID));
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, 0);
		calendar.setFirstDayOfWeek(Calendar.SUNDAY);

		if (calendar.get(Calendar.DAY_OF_WEEK) != day)
		{
			if (day > calendar.get(Calendar.DAY_OF_WEEK))
				calendar.add(Calendar.DAY_OF_YEAR, day - calendar.get(Calendar.DAY_OF_WEEK));

			if (day < calendar.get(Calendar.DAY_OF_WEEK))
			{
				calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
				if (day > 1)
					calendar.add(Calendar.DAY_OF_YEAR, 7 - (calendar.get(Calendar.DAY_OF_WEEK) - day));
			}
		} else
		{
			calendar.set(Calendar.DAY_OF_WEEK, day);
		}

		long currentTime = calendar.getTimeInMillis();
		if (calendar.before(Calendar.getInstance()))
			currentTime += AlarmManager.INTERVAL_DAY * 7;

		// alarm Id needs to be a reference to the database line for this notification
		Intent intent = new Intent(context, AlarmReceiver.class);
		intent.putExtra("alarm_id", alarmID);
		PendingIntent pedingIntent = PendingIntent.getBroadcast(context, Convert.safeLongToInt(alarmID), intent,
				PendingIntent.FLAG_UPDATE_CURRENT);

		// set the alarm to repeat every week at the same time
		alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, currentTime, AlarmManager.INTERVAL_DAY * 7, pedingIntent);
	}

	// TODO:This needs to be called when removing an item
	public void clearReminder(int alarmID)
	{
		Log.w(TAG, "clearReminder with alarmID = " + Long.toString(alarmID));
		Intent intent = new Intent(context, AlarmReceiver.class);

		PendingIntent sender = PendingIntent.getBroadcast(context, alarmID, intent, PendingIntent.FLAG_UPDATE_CURRENT);

		if (sender != null)
			alarmManager.cancel(sender);

		sender.cancel();
	}
}
