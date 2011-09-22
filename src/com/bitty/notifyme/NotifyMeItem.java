package com.bitty.notifyme;

import java.util.Date;

public class NotifyMeItem
{
	String task;
	Date created;
	
	public String getTask() {
		return task;
	}
	
	public Date getCreated() {
		return created;
	}
	
	public NotifyMeItem(String _task) {
		this(_task, new Date(java.lang.System.currentTimeMillis()));
	}

	public NotifyMeItem(String _task, Date _created) {
		task = _task;
		created = _created;
	}
	
}
