package org.cortelyoucollective.notifyme;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.util.Log;
import org.cortelyoucollective.notifyme.R;

public abstract class WakeReminderIntentService extends IntentService
{
	private static final String TAG = "WakeReminderIntentService";
	
	abstract void doReminderWork(Intent intent);

	public static final String LOCK_NAME_STATIC = "com.bitty.notifyme.Static";
	private static PowerManager.WakeLock lockStatic = null;

	public static void acquireStaticLock(Context context)
	{
		getLock(context).acquire();
	}

	synchronized private static PowerManager.WakeLock getLock(Context context)
	{
		Log.w(TAG, "PowerManager.WakeLock getLock");
		if (lockStatic == null)
		{
			PowerManager mgr = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
			lockStatic = mgr.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, LOCK_NAME_STATIC);
			lockStatic.setReferenceCounted(true);
		}
		return (lockStatic);
	}

	public WakeReminderIntentService(String name)
	{
		super(name);
	}

	@Override
	final protected void onHandleIntent(Intent intent)
	{
		Log.w(TAG, "onHandleIntent");
		
		try
		{
			doReminderWork(intent);
		} finally
		{
			if (lockStatic != null)
			{
				Log.w(TAG, "release lock?");
				getLock(this).release();
			}
		}
	}
}
