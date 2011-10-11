package com.bitty.notifyme;

import java.util.ArrayList;

import android.app.Application;

public class NotifyApplication extends Application{
	
	private ArrayList<NotifyMeItem> currentDaysNotifications;
	private ArrayList<MTAStatusItem> mtaStatusArray;
	private String currentDay;
	private int currentDayID;
	private NotifyDBAdapter notifyDB;
	
	public ArrayList<NotifyMeItem> getDailyNotificationArray() {
        return currentDaysNotifications;
	}
	
	public void setDailyNotificationArray(ArrayList<NotifyMeItem> notifications) {
		this.currentDaysNotifications = notifications;
	}
	
	public String getCurrentDayName() {
        return currentDay;
	}
	
	public void setCurrentDayName(String day) {
	        this.currentDay = day;
	}

	public int getCurrentDayID() {
		return currentDayID;
	}
	
	public void setCurrentDayID(int day) {
		this.currentDayID = day;
	}
	
	public void setMTAStatusArray(ArrayList<MTAStatusItem> arr){
		this.mtaStatusArray = arr;
	}
	
	public ArrayList<MTAStatusItem> getMTAStatusArray(){
		return mtaStatusArray;
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
		super.onTerminate();
		notifyDB.close();
	}
}
