package com.greatwall.platform.system.service;

import java.util.List;
import java.util.Map;

import com.greatwall.platform.domain.PageParameter;
import com.greatwall.platform.system.dto.Association;
import com.greatwall.platform.system.dto.Role;


public interface RoleService {

	public List<Role> getRoles(Role role,PageParameter page);
	
	public List<Role> getRoles(Role role);
	
	public List<Map<String, Object>> getRoleTree();
	
	public void saveUserRole(Integer userId,Integer roleId);
	
	public List<Association> getUserRoles(Integer userId);
}
