package com.dt.sample.js.rpc.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	public final static String FORMAT_STRING = "yyyy-MM-dd HH:mm:ss";

	public static Date str2Date(String dateString) {
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMAT_STRING);
			return simpleDateFormat.parse(dateString);
		} catch (ParseException e) {
			throw new RuntimeException("时间转化格式错误!" + "[dateString=" + dateString + "]" + "[FORMAT_STRING=" + FORMAT_STRING + "]");
		}
	}
}
