package com.greatwall.platform.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateUtils {

	private static final Pattern EMAIL_PATTERN  = 
			Pattern.compile("(?:\\w[-._\\w]*\\w@\\w[-._\\w]*\\w\\.\\w{2,3}$)");
	private static final Pattern NUM_PATTERN  = 
			Pattern.compile("^[0-9]*$");
	
	public static boolean isInteger(String num){
		Matcher m = NUM_PATTERN.matcher(num); 
		return m.matches();
	}
}
