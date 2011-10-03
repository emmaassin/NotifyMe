package com.bitty.notifyme;

import java.util.ArrayList;

import android.app.Application;

public class NotifyMeApplication extends Application{
	
	private ArrayList<NotifyMeItem> currentDaysNotifications;
	private String currentDay;
	
	public ArrayList getCurrentDaysNotifications() {
        return currentDaysNotifications;
	}
	
	public void setCurrentDaysNotifications(ArrayList notifications) {
	        this.currentDaysNotifications = notifications;
	}
	
	public String getCurrentDay() {
        return currentDay;
	}
	
	public void setCurrentDay(String day) {
	        this.currentDay = day;
	}
	
}
