package com.greatwall.platform.system.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.greatwall.platform.base.dao.MyBatisDao;
import com.greatwall.platform.system.dao.ResourceDao;
import com.greatwall.platform.system.dto.Resource;

/**   
 * @Description: 菜单、按钮资源 
 * @author fudk fudk_k@sina.com   
 * @date 2014年7月27日 上午11:49:54    
 */
@Repository
public class ResourceDaoImpl extends MyBatisDao implements ResourceDao {

	public List<Resource> getResources(Resource resource){
		return this.getList("resourceMapper.getResource",resource);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Resource> getResourcesIds(List<Integer> ids){
		Map param = new HashMap();
		param.put("ids", ids);
		return this.getList("resourceMapper.getResourceIds",param);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Resource> getResourceByAssociation(String destinationType,List<Integer> destinationValues,String resType){
		Map param = new HashMap();
		param.put("destinationType", destinationType);
		param.put("destinationValues", destinationValues);
		param.put("resType", resType);
		return this.getList("resourceMapper.getResourceByAssociation",param);
	}
	
	public Resource getResource(int resId){
		Resource res = new Resource();
		res.setResId(resId);
		return this.get("resourceMapper.getResource", res);
	}
	
	public void updateResource(Resource resource){
		this.update("resourceMapper.updateResource", resource);
	}
	
	public void updateResourceOrder(int parentId,int statOrder,int endOrder,String status){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("parentId", parentId);
		map.put("statOrder", statOrder);
		map.put("endOrder", endOrder);
		map.put("status", status);
		this.update("resourceMapper.updateResourceOrder", map);
	}

	public void saveResource(Resource resource){
		Integer maxOrder = this.get("getMaxOrder", resource.getParentId());
		maxOrder = maxOrder==null?0:maxOrder;
		resource.setResOrder(maxOrder+1);
		this.save("resourceMapper.insertResource",resource);
	}
	
	public void delResource(int resId){
		this.delete("resourceMapper.delResource", resId);
	}
}
