package com.besprout.voip.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DateUtil {

	public static String getCurrentWeekDisplay(String timeZone) {
		Calendar cal = Calendar.getInstance();
		Date today = DateUtils.getFullTimeTimeZoneNowForDate(timeZone);
		cal.setTime(today);
		int flag = cal.get(Calendar.DAY_OF_WEEK);
		switch (flag) {
		case Calendar.MONDAY:
			return "Monday";
		case Calendar.TUESDAY:
			return "Tuesday";
		case Calendar.WEDNESDAY:
			return "Wednesday";
		case Calendar.THURSDAY:
			return "Thursday";
		case Calendar.FRIDAY:
			return "Friday";
		case Calendar.SATURDAY:
			return "Saturday";
		case Calendar.SUNDAY:
			return "Sunday";
		default:
			return "";
		}
	}
	
	public static String getDbWeekDisplay(int index) {
		switch (index) {
		case 1:
			return "Monday";
		case 2:
			return "Tuesday";
		case 3:
			return "Wednesday";
		case 4:
			return "Thursday";
		case 5:
			return "Friday";
		case 6:
			return "Saturday";
		case 0:
			return "Sunday";
		default:
			return "";
		}
	}
	
	
	public static Date convertDate(String date, String pattern){
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		try {
			return format.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Date convertDate(String date, String pattern, Locale locale){
		SimpleDateFormat format = new SimpleDateFormat(pattern, locale);
		try {
			return format.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Date getDay(Date date, int afterDayNum){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, afterDayNum);
		return calendar.getTime();
	}
	
	public static String getDateString(Date date){
		return getDateString(date, "yyyy-MM-dd HH:mm:ss");
	}
	
	public static String getDateString(Date date, String pattern){
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(date);
	}
	
	public static boolean isSame(Date date1, Date date2){
		return date1.getTime() == date2.getTime();
	}
	
	/**
	 * date1 是否比 date2 大
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isBetterThan(Date date1, Date date2, String pattern){
		String s1 = getDateString(date1, pattern);
		String s2 = getDateString(date2, pattern);
		
		return s1.compareTo(s2) > 0;
	}
	
	/**
	 * 获取星期几 , 0为星期日
	 * @param date
	 * @return
	 */
	public static int getDayOfWeek(Date date){
		Calendar calender = Calendar.getInstance();
		calender.setTime(date);
		return calender.get(Calendar.DAY_OF_WEEK) -1;
	}
	
	/**
	 * 获取今天日期
	 * @param date
	 * @return
	 */
	public static String getDayOfCurrentDay(Date date){
		Calendar calender = Calendar.getInstance();
		calender.setTime(date);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(calender.getTime());
	}
	
	/**
	 * 获取本周的日期
	 * @param date
	 * @return
	 */
	public static List<String> getDayListOfWeek(Date date){
		List<String> list = new ArrayList<String>();
		Calendar calender = Calendar.getInstance(Locale.ENGLISH);
		calender.setTime(date);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//		calender.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY); //获取本周一的日期
		calender.setFirstDayOfWeek(1);
		
		calender.add(Calendar.DATE,-(calender.get(Calendar.DAY_OF_WEEK)-1));
		
		for (int i = 1; i <= 7; i++) {
			list.add(df.format(calender.getTime()));
			calender.add(Calendar.DATE,1);
		}
		return list;
	}
	
	/**
	 * 获取本星期的最后一天
	 * @param date
	 * @return
	 */
	public static Date getEndDayOfWeek(Date date){
		int dayOfWeek = DateUtil.getDayOfWeek(date);
		int left = 7 - dayOfWeek - 1;		//相差几天
		return DateUtil.getDay(date, left);
	}
	
	public static Date set(Date date, int field, int value){
		Calendar calender = Calendar.getInstance();
		calender.setTime(date);
		calender.set(field, value);
		return calender.getTime();
	}
	
	public static Date add(Date date, int field, int value){
		Calendar calender = Calendar.getInstance();
		calender.setTime(date);
		calender.add(field, value);
		return calender.getTime();
	}
	
	/**
	 * 获取指定时间月份的最后一天
	 * */
	public static Date getLastDayOfMonth(Date date) {
		Calendar cale = Calendar.getInstance();  
		cale.setTime(date);
		cale.set(Calendar.DAY_OF_MONTH, cale.getActualMaximum(Calendar.DAY_OF_MONTH));  
		return cale.getTime();
	}
	
	/**
	 * 获取指定时间月份的第一天
	 * */
	public static Date getFristDayOfMonth(Date date) {
		Calendar cale = Calendar.getInstance();  
		cale.setTime(date);
		cale.set(Calendar.DAY_OF_MONTH,1);;  
		return cale.getTime();
	}
	
	/**
	 * 获取两个时间段的月份
	 * */
	public static List<String> getMonthWithTwoTime(Date startTime, Date endTime) {
		List<String> list = new ArrayList<String>();
		try {
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM");
			Date d1 = new SimpleDateFormat("yyyy-MM").parse(sdf2.format(startTime));//定义起始日期
			Date d2 = new SimpleDateFormat("yyyy-MM").parse(sdf2.format(endTime));//定义结束日期
			Calendar dd = Calendar.getInstance();//定义日期实例
			dd.setTime(d1);//设置日期起始时间
			while(dd.getTime().before(d2) || dd.getTime().equals(d2)){//判断是否到结束日期
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
				String str = sdf.format(dd.getTime());
				list.add(str);
				dd.add(Calendar.MONTH, 1);//进行当前日期月份加1
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 获取当前时间的前6天的日期
	 * */
	public static Date getPreSevenDays() {
        Calendar c = Calendar.getInstance();  
        c.add(Calendar.DATE, - 6);  
        return c.getTime();
	}
	
	/**
	 * 获取当前时间的前N天的日期
	 * */
	public static Date getPreNDays(int num) {
        Calendar c = Calendar.getInstance();  
        c.add(Calendar.DATE, - num);  
        return c.getTime();
	}
	
	/**
	 * 获取指定时间的周第一天和最后一天日期
	 * */
	public static Date[] convertWeekByDate(Date time) {  
		Date[] dateArr = new Date[2];
		Calendar cal = Calendar.getInstance();  
		cal.setTime(time);  
		//判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了  
		int dayWeek = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天  
		if(1 == dayWeek) {  
			cal.add(Calendar.DAY_OF_MONTH, -1);  
		}  
	
		cal.setFirstDayOfWeek(Calendar.SUNDAY);//设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一  
		int day = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天  
		cal.add(Calendar.DATE, cal.getFirstDayOfWeek()-day);//根据日历的规则，给当前日期减去星期几与一个星期第一天的差值   
		dateArr[0] = cal.getTime();
		cal.add(Calendar.DATE, 6);  
		dateArr[1] = cal.getTime();  
		return dateArr;
	}  
	
	/**
	 * 获取指定时间的月第一天和最后一天日期
	 * */
	public static Date[] convertMonthByDate(Date time) {  
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date[] dateArr = new Date[2];
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(time);
			calendar.add(Calendar.MONTH, -1);
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			String baseDay = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
			String startDate = baseDay + " 00:00:00";
			dateArr[0] = sdf.parse(startDate);
			
			calendar = Calendar.getInstance();
			calendar.setTime(time);
			calendar.set(Calendar.DAY_OF_MONTH, 1); 
			calendar.add(Calendar.DATE, -1);
			
			baseDay = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
			String endDate = baseDay + " 23:59:59";
			dateArr[1] = sdf.parse(endDate);
			
			return dateArr;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
}

