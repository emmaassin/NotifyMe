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

	public int getDay() {
		return day;
	}
	
	public int getHour() {
		return hour;
	}

	public int getMinutes() {
		return minutes;
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
