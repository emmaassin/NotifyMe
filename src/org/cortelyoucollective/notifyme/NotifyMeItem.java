package org.cortelyoucollective.notifyme;

import java.util.List;
import org.cortelyoucollective.notifyme.R;

public class NotifyMeItem
{
	private List<String> trains;
	private int day;
	private int hour;
	private int minutes;
	private long db_ID;
	private String trainType;
	
	public List<String> getTrains() {
		return trains;
	}

	public void setTrains(List<String> arr) {
		trains = arr;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int _day) {
		day = _day;
	}
	
	public int getHour() {
		return hour;
	}

	public void setHour(int _hour) {
		hour = _hour;
	}

	public int getMinutes() {
		return minutes;
	}

	public void setMinutes(int _min) {
		minutes = _min;
	}

	public void setTrainType(String type){
		trainType = type;
	}

	public String getTrainType(){
		return trainType;
	}

	public long getDB_ID() {
		return db_ID;
	}
	
	public NotifyMeItem(List<String> trainsSelected, int _day, int _hour, int _minutes, String _type) {
		trains = trainsSelected;
		day = _day;
		hour = _hour;
		minutes = _minutes;
		trainType = _type;
	}

	public NotifyMeItem(List<String> trainsSelected, int _day, int _hour, int _minutes, String _type, long _db_ID) {
		trains = trainsSelected;
		day = _day;
		hour = _hour;
		minutes = _minutes;
		trainType = _type;
		db_ID = _db_ID;
	}
	
}
