package com.aylson.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;
import java.util.Random;

import org.apache.commons.lang.time.DateUtils;

public class DateUtil2 {
	// 由于AbleCloud时间设置，需加8个小时
		public static final int DATE_NUM = 0;

		public static final String LONG_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

		public static final String LONG_DATETIME_FORMAT_SSS = "yyyy-MM-dd HH:mm:ss.SSS";

		public static final String LONGDATETIMEFORMAT = "yyyyMMddHHmmss";

		private static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm";

		private static final String DATE_FORMAT = "yyyy-MM-dd";

		private static final String TIME_FORMAT = "HH:mm";

		/**
		 * 获取指定月的对应天数
		 */
		public static final int[] DAYS = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

		/**
		 * convert to millisecond
		 */
		public static final long TIME_ONE_HOUR = 3600L * 1000L;

		public static final long TIME_ONE_DAY = 24L * TIME_ONE_HOUR;

		/**
		 * Convert date to String like "yyyy-MM-dd HH:mm:ss".
		 */
		public static String getCurrentLongDateTime() {
			return new SimpleDateFormat(LONG_DATETIME_FORMAT).format(DateUtils.addHours(new Date(), DATE_NUM));
		}

		/**
		 * Convert date to String like "yyyy-MM-dd".
		 */
		public static String getCurrentDate() {
			return new SimpleDateFormat(DATE_FORMAT).format(DateUtils.addHours(new Date(), DATE_NUM));
		}

		/**
		 * 返回当前系统时间（字符串格式：yyyy-MM-dd HH:mm:ss）
		 * 
		 * @return String
		 */
		public static String getDateTime() {
			Date d = DateUtils.addHours(new Date(), DATE_NUM);
			SimpleDateFormat sdfreplay = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String replytime = sdfreplay.format(d);
			return replytime;
		}

		/**
		 * 返回一个随机数，组成格式"yyyyMMddhhmmss"+10000以内的随机数
		 * 
		 * @return
		 */
		public static String buildRandomFileName() {
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdfreplay = new SimpleDateFormat("yyyyMMddHHmmss");
			String replytime = sdfreplay.format(cal.getTime());
			return replytime + new Random().nextInt(1000);
		}

		/**
		 * Convert date to String like "yyyy-MM-dd HH:mm".
		 */
		public static String getCurrentDateTime() {
			return new SimpleDateFormat(DATETIME_FORMAT).format(DateUtils.addHours(new Date(), DATE_NUM));
		}
		
		public static String getCurrentDateByNum(int amount) {
			return new SimpleDateFormat(DATE_FORMAT).format(DateUtils.addDays(new Date(), amount));
		}

		/**
		 * Convert time to string like "HH:mm".
		 */
		public static String formatTime(Date d) {
			return new SimpleDateFormat(TIME_FORMAT).format(d);
		}

		/**
		 * Convert date and time to string like "yyyy-MM-dd HH:mm".
		 */
		public static String formatDateTime(Date d) {
			return new SimpleDateFormat(DATETIME_FORMAT).format(d);
		}

		/**
		 * Convert date to String like "yyyy-MM-dd".
		 */
		public static String formatDate(Date d) {
			return new SimpleDateFormat(DATE_FORMAT).format(d);
		}

		/**
		 * Convert date and time to string like "yyyy-MM-dd HH:mm:ss".
		 */
		public static String formatLongDate(Date d) {
			return new SimpleDateFormat(LONG_DATETIME_FORMAT).format(d);
		}

		/**
		 * Convert date and time to string like "yyyy-MM-dd HH:mm:ss SSS".
		 */
		public static String formatLongDateSSS(Date d) {
			return new SimpleDateFormat(LONG_DATETIME_FORMAT_SSS).format(d);
		}

		/**
		 * Parse date like "yyyy-MM-dd".
		 */
		public static Date parseDate(String d) {
			try {
				return new SimpleDateFormat(DATE_FORMAT).parse(d);
			} catch (ParseException e) {
			}
			return null;
		}

		/**
		 * Parse date and time like "yyyy-MM-dd hh:mm".
		 */
		public static Date parseDateTime(String dt) {
			try {
				return new SimpleDateFormat(DATETIME_FORMAT).parse(dt);
			} catch (Exception e) {
			}
			return null;
		}

		/**
		 * Parse date and time like "yyyy-MM-dd hh:mm:ss".
		 */
		public static Date parseLongDateTime(String dt, String format) {
			try {
				return new SimpleDateFormat(format).parse(dt);
			} catch (Exception e) {
			}
			return null;
		}

		/**
		 * Parse date and time like "yyyy-MM-dd hh:mm:ss".
		 */
		public static Date parseLongDateTime(String dt) {
			try {
				return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(dt);
			} catch (Exception e) {
			}
			return null;
		}

		/**
		 * Convert date and time to string like "yyyy-MM-dd HH:mm".
		 */
		public static String formatLongDateTime(Date d, String format) {
			return new SimpleDateFormat(format).format(d);
		}

		/*
		 * 判断date是否在开始日期和结束日期之间
		 */
		public static boolean includes(Date fromDay, Date toDay, Date date) {
			long fromTime = fromDay.getTime();
			long toTime = toDay.getTime();
			long dateTime = date.getTime();
			return ((fromTime <= dateTime) && (dateTime < toTime));
		}

		/*
		 * 判断date是否在开始日期和结束日期之间
		 * 
		 */
		public static boolean includess(Date fromDay, Date toDay, Date date) {
			long fromTime = fromDay.getTime();
			long toTime = toDay.getTime();
			long dateTime = date.getTime();
			return ((fromTime <= dateTime) && (dateTime <= toTime));
		}

		/*
		 * 给定的日期,增加指定的天数 除去周六周日之外
		 */
		public static String addDays(String dateStr, int days, Properties holidays) {
			SimpleDateFormat sf = new SimpleDateFormat(DATE_FORMAT);
			Date current = null;
			try {
				current = sf.parse(dateStr);
				int cuDay = 0;
				for (int i = 0; i < days; i++) {
					current.setDate(current.getDate() + 1);
					cuDay = current.getDay();
					/* 除去节假日 */
					Iterator iter = holidays.keySet().iterator();
					while (iter.hasNext()) {
						String key = (String) iter.next();
						if (key.startsWith("holiday.")) {
							String[] va = holidays.getProperty(key).split("&");
							boolean re = includes(sf.parse(va[0]), sf.parse(va[1]), current);
							if (re) {
								if (cuDay != 0 && cuDay != 6) {
									i--;
								}
							}
						}
					}
					/* 除去周六周日 */
					if (cuDay == 0 || cuDay == 6) {
						i--;
					}
				}
			} catch (ParseException e) {
				current = DateUtils.addHours(new Date(), DATE_NUM);
			}
			return sf.format(current);
		}

		/*
		 * 给定日期和系统当前日期的天数差 除去周六周日之外
		 */
		public static int getDayFromToday(String start, Properties holidays) {
			int day = -1;
			boolean negative = false;
			try {
				SimpleDateFormat sf = new SimpleDateFormat(DATE_FORMAT);
				Date startDate = sf.parse(start);
				Date endDate = DateUtils.addHours(new Date(), DATE_NUM);/* 系统当前日期 */
				int cuDay = 0;
				Date temp = null;
				if (endDate.before(startDate)) {
					temp = startDate;
					startDate = endDate;
					endDate = temp;
					negative = true;
				}
				while (endDate.compareTo(startDate) > 0) {
					startDate.setDate(startDate.getDate() + 1);
					cuDay = startDate.getDay();
					/* 除去节假日 */
					Iterator iter = holidays.keySet().iterator();
					while (iter.hasNext()) {
						String key = (String) iter.next();
						if (key.startsWith("holiday.")) {
							String[] va = holidays.getProperty(key).split("&");
							boolean re = includes(sf.parse(va[0]), sf.parse(va[1]), startDate);
							if (re) {
								if (cuDay != 0 && cuDay != 6) {
									day++;
								}
							}
						}
					}
					if (cuDay > 0 && cuDay < 6) {
						day++;
					}
				}
			} catch (ParseException e) {
			}

			return negative == true ? day : -day;
		}

		public static boolean isLeapYear(int year) {
			if (year % 100 == 0) {
				return year % 400 == 0;
			}
			return year % 4 == 0;
		}

		/**
		 * 将10-05-2013 转换为 2013-10-05
		 */
		public static String changedate(String d) {
			String ret = d;
			try {
				String tmp = d.substring(d.length() - 4, d.length());
				ret = tmp + "-" + d;
				ret = ret.substring(0, 10);
			} catch (Exception e) {
				return ret;
			}
			return ret;
		}

		/**
		 * 将01/10/2013 转换为 2013-10-01
		 */
		public static String changedates(String d) {
			String ret = d;
			try {
				String tmp = d.substring(3, 5);
				String tmpday = d.substring(0, 2);
				String tmpyew = d.substring(6, 10);
				ret = tmpyew + "-" + tmp + "-" + tmpday;

			} catch (Exception e) {
				return ret;
			}
			return ret;
		}

		/**
		 * 获取周几的中文名称，比如周一
		 * 
		 * @param key
		 * @return
		 */
		public static String getWeekDesc(String key) {
			String[][] keys = initWeeks();
			for (int i = 0; i < keys.length; i++) {
				String[] temp = keys[i];
				for (int j = 0; j < temp.length; j++) {
					if (key.equals(temp[0])) {
						return temp[1];
					}
				}
			}
			return "不存在对应的解析描述";
		}

		private static String[][] initWeeks() {
			String[][] KEY_VALUE = { { "1", "周一" }, { "2", "周二" }, { "3", "周三" }, { "4", "周四" }, { "5", "周五" },
					{ "6", "周六" }, { "7", "周日" } };
			return KEY_VALUE;
		}

		public static long getDateDays(Date nowDate, Date oldDate) {
			long nd = 1000 * 24 * 60 * 60;
			long nh = 1000 * 60 * 60;
			long nm = 1000 * 60;
			// long ns = 1000;
			// 获得两个时间的毫秒时间差异
			long diff = nowDate.getTime() - oldDate.getTime();
			// 计算差多少天
			// long day = diff / nd;
			// 计算差多少小时
			long hour = diff % nd / nh;
			// 计算差多少分钟
			// long min = diff % nd % nh / nm;
			// 计算差多少秒//输出结果
			// long sec = diff % nd % nh % nm / ns;
			// return day + "天" + hour + "小时" + min + "分钟";
			return hour;
		}

		// public static void main(String[] args) {
		// Date d1 = DateUtil.parseLongDateTime("2017-04-25 14:12:12");
		// Date d2 = DateUtil.parseLongDateTime("2017-04-25 11:12:11");
		// long a = getDateDays(d2, d1);
		// System.out.println(a);
		// }

		/**
		 * 根据日期取得星期几
		 * 
		 * @param date
		 * @return
		 */
		public static int getWeek(Date date) {
			String[] weeks = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
			if (week_index < 0) {
				week_index = 0;
			}
			if (week_index == 0) {
				week_index = 7; // 周日
			}
			return week_index;
			// SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
			// String week = sdf.format(date);
			// return week;
		}

		/**
		 * 得到指定日期在一个月中的哪一天
		 * @param date
		 * @return
		 */
		public static int getDayOfMonth(Date date) {
			Calendar ca=Calendar.getInstance();
			ca.setTime(date);
			int a=ca.get(Calendar.DAY_OF_MONTH);
			return a;
		}
}
