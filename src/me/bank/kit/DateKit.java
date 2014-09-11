package me.bank.kit;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateKit {

	public static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * 获取今天的日�?
	 * 
	 * @return
	 */
	public static String getToday() {
		formatter.applyPattern("yyyy-MM-dd");
		return formatter.format(new Date().getTime());
	}

	/**
	 * 获取当前年份
	 * 
	 * @return
	 */
	public static int getYear() {
		Calendar.getInstance().setTime(new Date());
		return Calendar.getInstance().get(Calendar.YEAR);
	}

	/**
	 * 获取当前月份
	 * 
	 * @return
	 */
	public static int getMonth() {
		Calendar.getInstance().setTime(new Date());
		return Calendar.getInstance().get(Calendar.MONTH) + 1;
	}

	/**
	 * 获取格式化的日期
	 * 
	 * @param time
	 * @return
	 */
	public static String format(long time) {
		return formatter.format(time);
	}

	/**
	 * 获取格式化的日期
	 * 
	 * @param time
	 * @return
	 */
	public static String format(String format, String time) {
		formatter.applyPattern(format);
		return formatter.format(Long.parseLong(time));
	}
	
	
	/**
	 * 根据日期字符串获取年�?
	 * @param format
	 * @param dateStr
	 * @return
	 * @throws ParseException
	 */
	@SuppressWarnings("deprecation")
	public static int getYear(String format, String dateStr) throws ParseException {
		formatter.applyPattern(format);
		Date date = formatter.parse(dateStr);
		return date.getYear() + 1900;
	}

	/**
	 * 根据日期字符串获取月�?
	 * @param format
	 * @param dateStr
	 * @return
	 * @throws ParseException 
	 */
	@SuppressWarnings("deprecation")
	public static int getMonth(String format, String dateStr) throws ParseException {
		formatter.applyPattern(format);
		Date date = formatter.parse(dateStr);
		
		return date.getMonth() + 1;
	}
	
	/**
	 * 获取今天的日期时�?
	 * 
	 * @return
	 */
	public static String getDateTime() {
		formatter.applyPattern("yyyy-MM-dd hh:mm:ss");
		return formatter.format(new Date());
	}
}
