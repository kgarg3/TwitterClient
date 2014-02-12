package com.codepath.apps.twittertimeline.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.format.DateUtils;


public class Util {

	/**
	 * Returns the relative timestamp for the given string. 
	 * @param activity
	 * @param fullTimestamp
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	public static String getRelativeTS(Context activity, String fullTimestamp) {
		//Fri Mar 05 07:30:19 +0000 2010
		SimpleDateFormat f = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy");
		Date d;
		String str;
		try {
			d = f.parse(fullTimestamp);
			long milliseconds = d.getTime();

			str = DateUtils.getRelativeDateTimeString(
					activity, // Suppose you are in an activity or other Context subclass
					milliseconds, // The time to display
					DateUtils.FORMAT_NUMERIC_DATE, // The resolution. This will display only minutes (no "3 seconds ago") 
					DateUtils.WEEK_IN_MILLIS, // The maximum resolution at which the time will switch 
					// to default date instead of spans. This will not 
					// display "3 weeks ago" but a full date instead
					0).toString(); // Eventual flags
			
		} catch (ParseException e) {
			e.printStackTrace();
			return fullTimestamp;
		}

		return str;
	}
	
	/**
	 * Calculates the relative timestamp for the given string and returns the numeric value of the time 
	 * along with the first char of the time length like 3h, 3m, 3d.
	 * @param activity
	 * @param fullTimestamp
	 * @return
	 */
	public static String getRelativeTSForTimeline(Context activity, String fullTimestamp) {
		String str = getRelativeTS(activity, fullTimestamp);
		
		//parse out only the number and the first char to get the time as 3h, 3m, 3d
		String[] tokens = str.split(" ");
		str = tokens[0] + tokens[1].charAt(0);
		
		return str;
	}
}
