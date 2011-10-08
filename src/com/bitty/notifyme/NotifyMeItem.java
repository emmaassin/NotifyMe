package com.bitty.notifyme;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NotifyMeItem
{
	private List<String> subways;
	private int day;
	private int hour;
	private int minutes;
	private long db_ID;
	
	public List<String> getSubways() {
		return subways;
	}

	public void setSubways(List<String> arr) {
		subways = arr;
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

	public long getDB_ID() {
		return db_ID;
	}

	public NotifyMeItem(List<String> subwaySelected, int _day, int _hour, int _minutes) {
		subways = subwaySelected;
		day = _day;
		hour = _hour;
		minutes = _minutes;
	}

	public NotifyMeItem(List<String> subwaySelected, int _day, int _hour, int _minutes, long _db_ID) {
		subways = subwaySelected;
		day = _day;
		hour = _hour;
		minutes = _minutes;
		db_ID = _db_ID;
	}
	
}
