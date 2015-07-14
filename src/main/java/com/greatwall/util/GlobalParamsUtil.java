package com.greatwall.util;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class GlobalParamsUtil {

	private static final  ConcurrentMap<String, Object> globalParams = new ConcurrentHashMap<>();
	
	private static final String SEARCH_LOCK = "searchLock";

	public static Boolean getSearchLock() {
		Integer searchLock = (Integer) globalParams.get(SEARCH_LOCK);
		if(searchLock==null){
			globalParams.putIfAbsent(SEARCH_LOCK, 1);
			return true;
		}else{
			if(searchLock==0){
				if(globalParams.replace(SEARCH_LOCK, 0, 1)){
					return true;
				}
			}
		}
		return false;
	}
	
	public static Boolean unSearchLock(){
		Integer searchLock = (Integer) globalParams.get(SEARCH_LOCK);
		if(searchLock!=null&&searchLock==1){
			if(globalParams.replace(SEARCH_LOCK, 1, 0)){
				return true;
			}
		}
		return false;
	}

	
	
}
