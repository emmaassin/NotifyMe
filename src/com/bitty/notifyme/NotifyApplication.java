package com.bitty.notifyme;

import java.util.ArrayList;

import android.app.Application;

public class NotifyApplication extends Application{
	
	private ArrayList<NotifyMeItem> currentDaysNotifications;
	private String currentDay;
	private NotifyDBAdapter notifyDB;
	
	public ArrayList getCurrentDaysNotifications() {
        return currentDaysNotifications;
	}
	
	public void setCurrentDaysNotificationArray(ArrayList notifications) {
		this.currentDaysNotifications = notifications;
	}
	
	public String getCurrentDay() {
        return currentDay;
	}
	
	public void setCurrentDay(String day) {
	        this.currentDay = day;
	}
	
	@Override
	public void onCreate()
	{
		super.onCreate();
		notifyDB = new NotifyDBAdapter(this);
		notifyDB.open();
	}
	
	public NotifyDBAdapter getNotifyDB()
	{
		return notifyDB;
	}
	
	@Override
	public void onTerminate()
	{
		// TODO Auto-generated method stub
		super.onTerminate();
		notifyDB.close();
	}
}
