package com.yzz.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {

	//时间戳Timestamp
	public static Timestamp getTimestamp() {

		return new Timestamp(System.currentTimeMillis());
	}
	
	//时间戳字符串
	public static String getCurrentTimeString(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		return sdf.format(new Date());
	}
	
	// 获取当前日期 毫秒
		public static long getTimeInMillis() {
			
			return Calendar.getInstance().getTimeInMillis();
		}

		// 获取当前日期 秒
		public static long getTimeInSeconds() {
			
			return getTimeInMillis() / 1000L;
		}

}
