package com.iLirium.utils.datetime;

import java.text.SimpleDateFormat;

/**
 * @author dopoljak@gmail.com
 *
 * Utility class for manipulating date and time
 * 
 */
public class Dates
{
	public static final String	LONG_DATE_FORMAT	= "yyyy-MM-dd HH:mm:ss:SS";
	public static final String	SHORT_DATE_FORMAT	= "HH:mm:ss:SSS";

	/**
	 * Get formated date
	 */
	public static String formatDate(String format, long date)
	{
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	/**
	 * Get current date
	 */
	public static String now()
	{
		SimpleDateFormat sdf = new SimpleDateFormat(LONG_DATE_FORMAT);
		// sdf.setTimeZone(TimeZone.getTimeZone("GMT+1:00"));
		return sdf.format(System.currentTimeMillis());
	}

	/**
	 * Format input milliseconds to: milliseconds, seconds, minutes, hours and days
	 */
	public static String getFormatedTime(long milliseconds)
	{
		long milisec = milliseconds % 1000;
		long seconds = (milliseconds / 1000) % 60;
		long minutes = (milliseconds / 60000) % 60;
		long hours = (milliseconds / 3600000) % 24;
		long days = milliseconds / 86400000;

		StringBuilder sb = new StringBuilder();
		if (days > 0)
			sb.append(days).append("d ");
		if (hours > 0)
			sb.append(hours).append("h ");
		if (minutes > 0)
			sb.append(minutes).append("m ");
		if (seconds > 0)
			sb.append(seconds).append("s ");
		if (milisec > 0)
			sb.append(milisec).append("ms");

		return sb.toString();
	}
}
