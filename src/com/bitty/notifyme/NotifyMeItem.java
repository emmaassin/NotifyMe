package com.bitty.notifyme;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NotifyMeItem
{
	private List<String> subways;
	int day;
	int hour;
	int minutes;
	
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

	public NotifyMeItem(List<String> subwaySelected, int _day, int _hour, int _minutes) {
		subways = subwaySelected;
		day = _day;
		hour = _hour;
		minutes = _minutes;
	}
	
}
