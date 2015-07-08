package com.greatwall.platform.system.dto;

import java.util.Comparator;

/**   
 * @Description: 菜单资源列表 
 * @author fudk fudk_k@sina.com   
 * @date 2014年7月15日 下午10:20:37    
 */
public class ResourceComparator implements Comparator<Resource> {
	
	public int compare(Resource o1, Resource o2) {
		if(o1.getParentId()<o2.getParentId()){
			return 1;
		}else if(o1.getParentId().equals(o2.getParentId())){
			if(o1.getResOrder()<o2.getResOrder()){
				return -1;
			}else{
				return 1;
			}
		}else{
			return -1;
		}
	}
	
}
