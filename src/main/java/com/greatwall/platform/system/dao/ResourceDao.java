package com.greatwall.platform.system.dao;

import java.util.List;

import com.greatwall.platform.system.dto.Resource;

public interface ResourceDao {
	
	public List<Resource> getResources(Resource resource);

	public Resource getResource(int resId);
	
	public void saveResource(Resource resource);
	
	public void updateResource(Resource resource);
	
	public void delResource(int resId);
	
	public void updateResourceOrder(int parentId,int statOrder,int endOrder,String status);
	
	public List<Resource> getResourceByAssociation(String destinationType,List<Integer> destinationValues,String resType);
	
	public List<Resource> getResourcesIds(List<Integer> ids);
}
