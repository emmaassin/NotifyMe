package com.bitty.notifyme;

import java.util.Date;

public class NotifyMeItem
{
	String lines;
	int day;
	int hour;
	int minutes;
	
	public String getLines() {
		return lines;
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

	public NotifyMeItem(String _lines, int _day, int _hour, int _minutes) {
		lines = _lines;
		day = _day;
		hour = _hour;
		minutes = _minutes;
	}
	
}
