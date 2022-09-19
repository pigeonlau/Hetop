package cn.edu.nwpu.rj416.util.types;

import cn.edu.nwpu.rj416.util.basic.Assert;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Harry
 * @since Harame3.0
 *
 */
//日期工具
public abstract class DateUtil {
	
	public static final String YMDHMSS = "yyyy-MM-dd HH:mm:ss SSS"; //年月日 时分秒 毫秒 格式
	public static final String YMDHMS = "yyyy-MM-dd HH:mm:ss"; //年月日 时分秒 格式
	public static final String YMDHM = "yyyy-MM-dd HH:mm"; //年月日 时分 格式
	public static final String YMDH = "yyyy-MM-dd HH"; //年月日 时 格式
	public static final String YMD = "yyyy-MM-dd"; //年月日 格式

	//将对象o转成日期Date对象
	public static Date toDate(Object o) {
		if (o == null) {
			return null;
		}
		
		if (o instanceof String) {
			String v = (String)o; //先转字符串
			SimpleDateFormat sdf;
			
			v = v.trim(); //去掉首尾空格
			int len = v.length();
			if (len == YMDHMSS.length()) {
				sdf = new SimpleDateFormat(YMDHMSS);
			} else if (len == YMDHMS.length()) {
				sdf = new SimpleDateFormat(YMDHMS);
			} else if (len == YMDHM.length()) {
				sdf = new SimpleDateFormat(YMDHM);
			} else if (len == YMDH.length()) {
				sdf = new SimpleDateFormat(YMDH);
			} else if (len == YMD.length()) {
				sdf = new SimpleDateFormat(YMD);
			} else {
				return null;
			} //长度上符合哪种格式就格式化为对应的日期格式化对象
			
			try {
				return sdf.parse(v); //日期格式化对象转换成日期对象返回
			} catch (ParseException e) {
				return null; //转换解析异常，返回空
			}
		} else if (o instanceof Date) {
			return (Date)o; //本身是日期对象直接返回
		} else if (o instanceof Long) {
			Long v = (Long)o;
			
			return new Date(v); //长整型可直接处理
		}
		
		return null;
	}
	
	/**
	 * Returns current time
	 * 
	 * @return the current time
	 * @since 3.0
	 */
	//返回当前日期时间的日期对象
	public static Date now() {
		return new Date();
	}
	
	/**
	 * 返回一个未来的时间
	 * 
	 * @param ms 从现在起的毫秒数
	 * @return 
	 * @since 3.0
	 */
	public static Date future(long ms) {
		return new Date(System.currentTimeMillis() + ms);
	}

	/**
	 * Returns current date
	 * 
	 * @return the current date without time part
	 * @since 3.0
	 */
	public static Date today() {
		return clearTime(now());
	}
	
	//创建一个指定日期时间的日期Date对象
	public static Date create(long time) {
		return new Date(time);
	}

	/**
	 * Returns a calendar based on the current time
	 * 
	 * @return a calendar
	 * @since 3.0
	 */
	//返回基于当前时间的日历对象
	public static Calendar calendar() {
		return Calendar.getInstance();
	}

	/**
	 * Returns a calendar based on the time
	 * 
	 * @param date the date to build the calendar
	 * @return a calendar
	 * @since 3.0
	 */
	//返回基于给定日期的日历对象
	public static Calendar calendar(Date date) {
		Assert.notNull(date, "Argument date cannot be null");
		Calendar calendar = calendar();
		calendar.setTime(date);
		return calendar;
	}

	/**
	 * Clears up the time part of the date
	 * 
	 * @param date the date to clear
	 * @return the date without time part
	 * @since 3.0
	 */
	//去除时间只留下日期部分
	public static Date clearTime(Date date) {
		Calendar calendar = calendar(date); //创建基于给定日期时间的日历对象
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.clear(Calendar.MINUTE);
		calendar.clear(Calendar.SECOND);
		calendar.clear(Calendar.MILLISECOND);
		return calendar.getTime();
	}

	/**
	 * Gets year of the specified calendar.
	 * 
	 * @param calendar the calendar
	 * @return year
	 * @throws IllegalArgumentException If the calendar is null
	 * @since 3.0
	 */
	//获取日历对象的年份
	public static int getYear(Calendar calendar) {
		Assert.notNull(calendar, "Argument calendar cannot be null");
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * Gets year of the date.
	 * 
	 * @param date the date
	 * @return year
	 * @throws IllegalArgumentException If the date is null
	 * @since 3.0
	 */
	//获取日期对象的年份
	public static int getYear(Date date) {
		Assert.notNull(date, "Argument date cannot be null");
		return getYear(calendar(date));
	}

	/**
	 * Gets month of the specified calendar.
	 * 
	 * @param calendar the calendar
	 * @return month
	 * @throws IllegalArgumentException If the calendar is null
	 * @since 3.0
	 */
	//获取日历对象的月份
	public static int getMonth(Calendar calendar) {
		Assert.notNull(calendar, "Argument calendar cannot be null");
		return calendar.get(Calendar.MONTH) + 1;
	}

	/**
	 * Gets month of the date.
	 * 
	 * @param date the date
	 * @return month
	 * @throws IllegalArgumentException If the date is null
	 * @since 3.0
	 */
	//获取日期对象的月份
	public static int getMonth(Date date) {
		Assert.notNull(date, "Argument date cannot be null");
		return getMonth(calendar(date));
	}

	/**
	 * Gets day of the specified calendar.
	 * 
	 * @param calendar the calendar
	 * @return day
	 * @throws IllegalArgumentException If the calendar is null
	 * @since 3.0
	 */
	//获取日历对象对应月份的天
	public static int getDay(Calendar calendar) {
		Assert.notNull(calendar, "Argument calendar cannot be null");
		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * Gets day of the date.
	 * 
	 * @param date the date
	 * @return day
	 * @throws IllegalArgumentException If the date is null
	 * @since 3.0
	 */
	//获取日期对象的天
	public static int getDay(Date date) {
		Assert.notNull(date, "Argument date cannot be null");
		return getDay(calendar(date));
	}

	/**
	 * Returns a new {@link DateFormat} with the specified pattern.
	 * 
	 * @param pattern the pattern
	 * @return a new {@link DateFormat}
	 * @exception IllegalArgumentException If the given pattern is invalid
	 * @since 3.0
	 */
	//以具体模式返回一个新的日期格式化对象
	public static DateFormat newDateFormat(String pattern) {
		Assert.notEmpty(pattern, "Argument pattern cannot be null or empty");
		DateFormat format = new SimpleDateFormat(pattern);
		return format;
	}

	/**
	 * Parses text from the given string to produce a date.
	 * 
	 * @param string the string
	 * @param pattern the pattern
	 * @return a date parsed from the string
	 * @throws ParseException If the string cannot be parsed
	 * @since 3.0
	 */
	//以指定格式解析一个字符串返回其对应日期
	public static Date parse(String string, String pattern) throws ParseException {
		Assert.notEmpty(string, "Argument cannot be null or empty");
		return newDateFormat(pattern).parse(string);
	}

	/**
	 * Formats a Date into a date/time string.
	 * 
	 * @param date the time value to be formatted into a time string
	 * @param pattern the pattern describing the date and time format
	 * @return the formatted time string
	 * @throws IllegalArgumentException If the date is null
	 * @since 3.0
	 */
	//将日期对象以指定格式转成字符串
	public static String format(Date date, String pattern) {
		Assert.notNull(date, "Argument cannot be null");
		return newDateFormat(pattern).format(date);
	}
}
