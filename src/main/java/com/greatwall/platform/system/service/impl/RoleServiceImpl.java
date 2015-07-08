package com.greatwall.platform.system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.greatwall.platform.domain.PageParameter;
import com.greatwall.platform.system.dao.RoleDao;
import com.greatwall.platform.system.dto.Association;
import com.greatwall.platform.system.dto.Role;
import com.greatwall.platform.system.service.AssociationService;
import com.greatwall.platform.system.service.RoleService;

@Service("roleService")
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private AssociationService associationService;
	
	
	
	public List<Role> getRoles(Role role,PageParameter page){
		return roleDao.getRoles(role, page);
	}
	
	public List<Role> getRoles(Role role){
		return roleDao.getRoles(role);
	}
	
	public List<Map<String, Object>> getRoleTree(){
		List<Map<String, Object>> treeMap = new ArrayList<Map<String, Object>>();
		List<Role> roles = roleDao.getRoles(null);
		if(roles!=null&&roles.size()>0){
			for(Role role:roles){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", role.getRoleId());
				map.put("pId", 0);
				map.put("name", role.getRoleName());
				map.put("open", true);
				treeMap.add(map);
			}
		}
		return treeMap;
	}
	
	public void saveUserRole(Integer userId,Integer roleId){
		Association ass = new Association();
		ass.setDestinationType("user");
		ass.setDestinationValue(userId);
		ass.setSourceType("role");
		ass.setSourceValue(roleId);
		
		associationService.saveAssociation(ass);
	}
	
	public List<Association> getUserRoles(Integer userId){
		Association ass = new Association();
		ass.setDestinationType("user");
		ass.setDestinationValue(userId);
		ass.setSourceType("role");

		return associationService.getAssociations(ass);
		 
	}
}
