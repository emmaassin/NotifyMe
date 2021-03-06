package org.cortelyoucollective.notifyme;

public class MTAStatusItem
{
	private static final String TAG = "StatusItem";

	private String lineName;
	private String status;
	private String statusText;
	private String date;
	private String time;
	private String transitType;

	// line, status, statusText, url, date, time
	
	public MTAStatusItem(String _line, String _status, String _statusTxt, String _date, String _time, String _type)
	{
		lineName = _line;
		status = _status;
		date = _date;
		time = _time;
		transitType = _type;
		statusText = "<i>Posted " + date + " " + time + "</i><br/>" + lineName + "<br/>" + status + "<br/><br/>"
				+ _statusTxt;
	}

	public String getLine()
	{
		return lineName;
	}

	public String getStatus()
	{
		return status;
	}

	public String getStatusText()
	{
		return statusText;
	}
	
	public String getDate()
	{
		return date;
	}
	
	public String getTime()
	{
		return time;
	}

	public String getTransitType()
	{
		return transitType;
	}

	@Override
	public String toString()
	{
		return lineName + " " + status;
	}

}
