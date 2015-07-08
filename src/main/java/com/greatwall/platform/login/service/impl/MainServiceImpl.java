package com.greatwall.platform.login.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.greatwall.platform.login.service.MainService;
import com.greatwall.platform.system.dto.Resource;
import com.greatwall.platform.system.dto.User;
import com.greatwall.platform.system.service.ResourceService;

/**   
 * @Description: 首页展示 
 * @author fudk fudk_k@sina.com   
 * @date 2014年7月16日 下午7:44:08    
 */
@Service("mainService")
public class MainServiceImpl implements MainService {

	
	@Autowired
	private ResourceService resourceService;
	
	/**
	 * @Title: getResource
	 * @Description: 得到有权限的资源列表
	 * @param @param user
	 * @param @return    设定文件
	 * @return List<Resource>    返回类型
	 * @throws
	 */
	public List<Resource> getResource(User user,String resType){
		return resourceService.getResources(user,resType);
	}
	
}
