package com.besprout.voip.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


public class DateUtils {
	
	
	private static SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
	
	private static SimpleDateFormat ydmDate = new SimpleDateFormat("yyyyMMdd");
	
	private static SimpleDateFormat usaDate = new SimpleDateFormat("MM/dd/yyyy");
	
	private static  SimpleDateFormat usaShowDate = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
	
	private static  SimpleDateFormat monDate = new SimpleDateFormat("yyyyMM", Locale.ENGLISH);
	
	
	/**
	 * 
	 * @param date :yyyyMM
	 * @return
	 */
	public static String getDateFormateFormMon(Date date) {
		if(date==null){
			return "";
		}
		return monDate.format(date);
	}
	
	
	/**
	 * 
	 * @param date :yyyy-MM-dd
	 * @return
	 */
	public static String getDateFormate(Date date) {
		if(date==null){
			return "";
		}
		return sdfDate.format(date);
	}
	
	/**
	 * 
	 * @param date :yyyyMMdd
	 * @return
	 */
	public static String getDateFormateYMD(Date date) {
		if(date==null){
			return "";
		}
		return ydmDate.format(date);
	}
	
	/**
	 * 
	 * @param date :yyyyMMdd
	 * @return
	 */
	public static String getDateFormateUSA(Date date) {
		if(date==null){
			return "";
		}
		return usaDate.format(date);
	}
	
	/**
	 * 
	 * @param date :MMM dd, yyyy
	 * @return
	 */
	public static String getDateFormateUSAShow(Date date) {
		if(date==null){
			return "";
		}
		return usaShowDate.format(date);
	}
	
	
	public static Date getParseUSADate(String date){
		try{
			if(date==null||date.length()<6){
				return null;
			}
		 return	usaDate.parse(date);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 
	 * @param date
	 * @return 1,2,3,4,5,6,0(星期一到，星期天)
	 */
	public static String getDateNum(Date date) {

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int flag = cal.get(Calendar.DAY_OF_WEEK);
		switch (flag) {
		case Calendar.MONDAY:
			return "1";
		case Calendar.TUESDAY:
			return "2";
		case Calendar.WEDNESDAY:
			return "3";
		case Calendar.THURSDAY:
			return "4";
		case Calendar.FRIDAY:
			return "5";
		case Calendar.SATURDAY:
			return "6";
		case Calendar.SUNDAY:
			return "0";
		default:
			return "";
	}
	}
	
	public static Date parseDate(String dateStr){
		try{
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if(!StringUtil.isEmpty(dateStr)){
				if(dateStr.length()<=10){
					dateStr = dateStr+" 23:59:59";
				}
				Date start=sdf1.parse(dateStr);
				return start;
		    }
		}catch (Exception e) {
		    e.printStackTrace();
	    }
		return null;
	}
	
	/**
	 * 取得值 xxxx-xx-xx xx:xx:xx
	 * @return
	 */
	private static Date getDay(String str){
		try{
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    return sdf1.parse(str);
		}catch (Exception e) {
		    e.printStackTrace();
	    }
		return null;
	}
	
	/**
	 * 取得今天最大值 xxxx-xx-xx 23:59:59
	 * @return
	 */
	public static Date getMaxToday(){
		return getDay(getMaxTodayStr());
	}
	
	/**
	 * 取得今天最大值 xxxx-xx-xx 23:59:59
	 * @return
	 */
	public static String getMaxTodayStr(){
		String dateStr=sdfDate.format(new Date())+" 23:59:59";
		return dateStr;
	}
	
	
	/**
	 * 取得今天最小值 xxxx-xx-xx 00:00:00
	 * @return
	 */
	public static Date getMinToday(){
		return getDay(getMinTodayStr());
	}
	
	/**
	 * 取得今天最小值 xxxx-xx-xx 00:00:00
	 * @return
	 */
	public static String getMinTodayStr(){
		String dateStr=sdfDate.format(new Date())+" 00:00:00";
		return dateStr;
	}
	
	
	public static Date timeCal(int days){
		Calendar calendar=Calendar.getInstance();  
		calendar.set(Calendar.DAY_OF_MONTH,calendar.get(Calendar.DAY_OF_MONTH)+days);
		Date date = calendar.getTime();
		return date;
	}
	
	
	/**
	 * 取得date最小值  xxxx-xx-xx 00:00:00
	 * @return
	 */
	public static Date convertMinDate(Date date){
		if(date==null){
			return null;
		}
		String dateStr=sdfDate.format(date)+" 00:00:00";
		return getDay(dateStr);
	}
	
	/**
	 * 取得date最大值  xxxx-xx-xx 23:59:59
	 * @return
	 */
	public static Date convertMaxDate(Date date){
		if(date==null){
			return null;
		}
		String dateStr=sdfDate.format(date)+" 23:59:59";
		return getDay(dateStr);
	}
	
	

	
	/**
	 * 直接转
	 * @param lastDate
	 * @return
	 */
	public static String getDisplayTimeDic(Date lastDate,String timeZone) {
		SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy h:mm aaa", Locale.ENGLISH);
		String displayTime = sdf.format(lastDate)+ " "+timeZone;
		return displayTime;
	}
	
	
	
	public static String getGroupTime(Date lastDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("E, MMM dd, yyyy", Locale.ENGLISH);
		TimeZone tz = MyUtil.getTimeZone("Eastern");
		sdf.setTimeZone(tz);
		return sdf.format(lastDate)+ " EST";
	}
	
	/**
	 * 时区转换
	 * @param date
	 * @param oldZone
	 * @param newZone
	 * @return
	 */
	 public static Date changeTimeZone(Date date, TimeZone oldZone, TimeZone newZone) {
	    // Date dateTmp = null;
	    // int timeOffset = oldZone.getRawOffset() - newZone.getRawOffset();
	   //  dateTmp = new Date(date.getTime()- timeOffset);
        try{
		SimpleDateFormat soldZone = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dd=soldZone.format(date);
		soldZone.setTimeZone(oldZone);
		Date d2=soldZone.parse(dd);
		SimpleDateFormat snewZone = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		snewZone.setTimeZone(newZone);
		String d3= snewZone.format(d2);
		return getDay(d3);
		}catch (Exception e) {
			return null;
		}
	     
	 }
	 
	 
	 public static String getFullTimeUTCNow(){
			return getFullTimeTimeZoneNow("UTC");
	 }
	 
	 public static Date getFullTimeUTCNowForDate(){
		return getDay(getFullTimeUTCNow());
	 }
	 
	 
	 public static String getFullTimeTimeZoneNow(String timeZone){
		      TimeZone tz = MyUtil.getTimeZone(timeZone);
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sdf1.setTimeZone(tz);
			Date today = new Date();
			return sdf1.format(today);
     }
	 
	 public static String getFullTimeTimeZone(String timeZone, Date date){
	    TimeZone tz = MyUtil.getTimeZone(timeZone);
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sdf1.setTimeZone(tz);
		return sdf1.format(date);
	}
	 
	 public static Date getFullTimeTimeZoneNowForDate(String timeZone){
		 return getDay(getFullTimeTimeZoneNow(timeZone));
	 }
	 
	 public static Date getFullTimeUTCForDate(Date date){
		 return getDay(getFullTimeTimeZone("UTC", date));
	 }
	 
	 public static Date getFullTimeTimeZoneForDate(String timeZone, Date date){
		 return getDay(getFullTimeTimeZone(timeZone, date));
	 }
	 
}
