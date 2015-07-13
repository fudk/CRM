package com.greatwall.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.greatwall.api.domain.RechargeCondition;

public class StringUtil {
	
	public static String[] getRepStrings(String text){
		String[] searchStr = {"，","；","'",";","。","."," ","/b","/t","/n","/f","/r"};
		return replaceAll(text,searchStr,",");
	}
	
	public static String[] replaceAll(String text, String[] searchString,String replacement){
		String repStr = StringUtils.trimToEmpty(text);
		if(StringUtils.isNotBlank(text)
				&&StringUtils.isNotBlank(replacement)
				&&ArrayUtils.isNotEmpty(searchString)){
			for(String s : searchString){
				repStr = StringUtils.replace(repStr, s, replacement);
			}
			return StringUtils.strip(repStr,replacement).split(replacement);
		}
		return null;
	}
	
	public static Boolean authRechargeCondition(RechargeCondition rechargeCondition,String key){
		StringBuffer sb = new StringBuffer();
		sb.append("platId=");
		sb.append(rechargeCondition.getPlatId());
		sb.append("&timestamp=");
		sb.append(rechargeCondition.getTimestamp());
		sb.append("&orderId=");
		sb.append(rechargeCondition.getOrderId());
		sb.append("&opType=");
		sb.append(rechargeCondition.getOpType());
		sb.append("&flxTyp=");
		sb.append(rechargeCondition.getFlxTyp());
		sb.append("&custPhone=");
		sb.append(rechargeCondition.getCustPhone());
		sb.append("&opPrice=");
		sb.append(rechargeCondition.getOpPrice());
		sb.append("&opNum=");
		sb.append(rechargeCondition.getOpNum());
		sb.append("&notifyUrl=");
		sb.append(rechargeCondition.getNotifyUrl());
		sb.append("&");
		sb.append(key);
		
		if(DigestUtils.md5Hex(sb.toString()).equals(rechargeCondition.getSign().toLowerCase())){
			return true;
		}
		return false;
	}
	
	public static void main(String[] args) {
		String ss = "s，s s,s;s，s，";
		
		System.out.println(ArrayUtils.toString(getRepStrings(ss)));
	}

}
