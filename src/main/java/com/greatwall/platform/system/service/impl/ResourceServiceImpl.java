package com.greatwall.platform.system.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.greatwall.platform.system.dao.ResourceDao;
import com.greatwall.platform.system.dto.Association;
import com.greatwall.platform.system.dto.Resource;
import com.greatwall.platform.system.dto.ResourceComparator;
import com.greatwall.platform.system.dto.User;
import com.greatwall.platform.system.service.AssociationService;
import com.greatwall.platform.system.service.ResourceService;

@Service("defaultResourceService")
public class ResourceServiceImpl implements ResourceService {

	@Autowired 
	private ResourceDao resourceDao;
	
	@Autowired
	private AssociationService associationService;
	
	/**
	 * @Title: getResource
	 * @Description: 得到有权限的资源列表
	 * @param @param user
	 * @param @return    设定文件
	 * @return List<Resource>    返回类型
	 * @throws
	 */
	public List<Resource> getResources(User user,String resType){
		List<Resource> reses = this.getRoleResources(user.getUserId(),resType);
		
		Set set = new HashSet();
        List<Resource> newList = new ArrayList<Resource>();
        for (Iterator iter = reses.iterator(); iter.hasNext();) {
        	Resource element = (Resource)iter.next();
            if (set.add(element.getResId()))
                newList.add(element);
        }
		
		return newList;
	}
	
	
	public List<Resource> getRoleResources(Integer userId,String resType){
		Association ass = new Association();
		ass.setDestinationType("user");
		ass.setDestinationValue(userId);
		ass.setSourceType("role");
		
		List<Association> assList = associationService.getAssociations(ass);
		List<Integer> dValues = new ArrayList<Integer>();
		if(assList!=null&&assList.size()>0){
			for(Association assf : assList){
				dValues.add(assf.getSourceValue());
			}
		}
		
		List<Resource> resChildrens = resourceDao.getResourceByAssociation("role",dValues,resType);
		
		List<Resource> reses = new ArrayList<Resource>();
		reses.addAll(resChildrens);
		Boolean hasParent = true;
		while(hasParent){
			resChildrens = this.getParent(resChildrens);
			if(resChildrens!=null&&resChildrens.size()>0){
				reses.addAll(resChildrens);
			}else{
				hasParent = false;
			}
		}
		Comparator<Resource> ascComparator = new ResourceComparator();
		// 利用Collections类静态工具方法对集合List进行排序
		Collections.sort(reses, ascComparator);
		return reses;
	}
	
	private List<Resource> getParent(List<Resource> resChildrens){
		List<Integer> pIds = new ArrayList<Integer>();
		HashSet set = new HashSet();
		if(resChildrens!=null&&resChildrens.size()>0){
			for(Resource res:resChildrens){
				if(res.getParentId()!=0){
					set.add(res.getParentId());
				}
			}
			pIds.addAll(set);
		}
		if(pIds!=null&&pIds.size()>0){
			return resourceDao.getResourcesIds(pIds);
		}else{
			return null;
		}
		
		
	}
	
	public JSONArray getResourcesTree(User user){
		 return this.resourcesToJSONArray(resourceDao.getResources(null));
	}
	
	public Resource getResource(int resId){
		return resourceDao.getResource(resId);
	}
	
	public void updateResource(Resource resource){
		resourceDao.updateResource(resource);
	}
	
	public void updateResourceParent(Resource resource){
		String status = "";
		int parentId = 0;
		int statOrder = 0;
		int endOrder = 0;
		Resource oldres = resourceDao.getResource(resource.getResId());
		parentId = resource.getParentId();
		if(oldres.getParentId().equals(resource.getParentId())){//父节点相同，则为同级排序
			if(oldres.getResOrder()>resource.getResOrder()){
				status = "add";
				statOrder = resource.getResOrder();
				endOrder = oldres.getResOrder();
			}else if(oldres.getResOrder()<resource.getResOrder()){
				status = "res";
				statOrder = oldres.getResOrder();
				endOrder = resource.getResOrder();
			}
			resourceDao.updateResourceOrder(parentId, statOrder, endOrder, status);
		}else{
			statOrder = resource.getResOrder();
			endOrder = -1;
			status = "add";
			resourceDao.updateResourceOrder(parentId, statOrder, endOrder, status);
			
			parentId = oldres.getParentId();
			statOrder = oldres.getResOrder();
			status = "res";
			resourceDao.updateResourceOrder(parentId, statOrder, endOrder, status);
		}
		
		resourceDao.updateResource(resource);
	}
	
	
	public Resource saveResource(Resource resource){
		resourceDao.saveResource(resource);
		List<Resource> reses = resourceDao.getResources(resource);
		if(reses!=null&&reses.size()>0){
			return reses.get(0);
		}else{
			return null;
		}
	}
	
	public void delResource(int resId){
		resourceDao.delResource(resId);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private JSONArray resourcesToJSONArray(List<Resource> reses){
		if(reses!=null&&reses.size()>0){
			List<Map> treeMap = new ArrayList<Map>();
			for(Resource res:reses){
				Map map = new HashMap();
				map.put("id", res.getResId());
				map.put("pId", res.getParentId());
				map.put("name", res.getResName());
				if(res.getParentId()!=null&&res.getParentId()==0){
					map.put("open", true);
				}
				treeMap.add(map);
			}
			return JSONArray.fromObject(treeMap);
		}else{
			return null;
		}
	}
}
