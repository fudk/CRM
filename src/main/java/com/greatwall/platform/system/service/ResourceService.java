package com.greatwall.platform.system.service;

import java.util.List;

import net.sf.json.JSONArray;

import com.greatwall.platform.system.dto.Resource;
import com.greatwall.platform.system.dto.User;

public interface ResourceService {

	public List<Resource> getResources(User user,String resType);
	
	public JSONArray getResourcesTree(User user);
	
	public Resource getResource(int resId);
	
	public Resource saveResource(Resource resource);
	
	public void updateResource(Resource resource);
	
	public void delResource(int resId);
	
	public void updateResourceParent(Resource resource);
	
	
}
