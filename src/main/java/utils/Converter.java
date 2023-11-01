package utils;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Converter {
	
	public static long AmountOfLockdownDays(){
		//First day: 30/10/2020
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(2020, 9, 29);
		Date date2 = calendar.getTime();
		Date date1 = new Date();
	    return ((date1.getTime() - date2.getTime()) / (86400000));
	}
	
	public static long AmountOfMinutesBeforeChristmas(){
		Calendar calendar = Calendar.getInstance();
		Calendar calendar2 = Calendar.getInstance();
		//First day: 25/12/2020
		
		calendar.set(2020, 11, 24, 0, 0, 0);
		Date date2 = calendar.getTime();
		calendar2.set(Calendar.HOUR_OF_DAY, Calendar.HOUR_OF_DAY);
		calendar2.set(Calendar.MINUTE, 0);
		calendar2.set(Calendar.SECOND, 0);
		
		
		//Date date1 = new Date();
		Date date1 = calendar2.getTime();
		long duration  = date2.getTime() - date1.getTime();
	    //return ((date2.getTime() - date1.getTime()) / (60000));
		return TimeUnit.MILLISECONDS.toMinutes(duration);
	}
	
	public static String TimeConverter(long seconds){
		
		String upTime = "";
		int[] TimeTable = {0, 0, 0, 0, 0};
		
		//Add Weeks
		while(seconds >= 604800){
			TimeTable[0]++;
			seconds -= 604800;
		}
		
		//Add Days
		while(seconds >= 86400){
			TimeTable[1]++;
			seconds -= 86400;
		}
		
		//Add Hours
		while(seconds >= 3600){
			TimeTable[2]++;
			seconds -= 3600;
		}
		
		//Add Minutes
		while(seconds >= 60){
			TimeTable[3]++;
			seconds -= 60;
		}
		
		//Add Seconds
		while(seconds >= 0){
			TimeTable[4]++;
			seconds -= 1;
		}
		
		if(TimeTable[0] != 0){
			upTime += TimeTable[0] + " week(s), ";
		}
		
		if(TimeTable[1] != 0){
			upTime += TimeTable[1] + " day(s), ";
		}
		
		if(TimeTable[2] != 0){
			upTime += TimeTable[2] + " hour(s), ";
		}
		
		if(TimeTable[3] != 0){
			upTime += TimeTable[3] + " minute(s), ";
		}
		
		if(TimeTable[4] != 0){
			upTime += TimeTable[4] + " second(s)";
		}
		
		return upTime;
	}
}
