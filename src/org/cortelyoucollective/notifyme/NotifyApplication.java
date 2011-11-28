package org.cortelyoucollective.notifyme;

import java.util.ArrayList;
import org.cortelyoucollective.notifyme.R;

import android.app.Application;

public class NotifyApplication extends Application{
	
	private ArrayList<NotifyMeItem> currentDaysNotifications;
	private ArrayList<MTAStatusItem> mtaStatusArray;
	private String currentDay;
	private int currentDayID;
	private String currentTrainType;
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
	
	public void setCurrentTrainType(String trainType)
	{
		currentTrainType = trainType;
	}
	
	public String getCurrentTrainType() {
		return currentTrainType;
	}
	
	@Override
	public void onCreate()
	{
		super.onCreate();
		setDB();
	}
	
	public NotifyDBAdapter getNotifyDB()
	{
		if(notifyDB == null)
			setDB();
			
		return notifyDB;
	}
	
	@Override
	public void onTerminate()
	{
		super.onTerminate();
		notifyDB.close();
	}
	
	private void setDB()
	{
		notifyDB = new NotifyDBAdapter(this);
		notifyDB.open();
	}
}
