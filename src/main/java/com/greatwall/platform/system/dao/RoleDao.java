package com.greatwall.platform.system.dao;

import java.util.List;

import com.greatwall.platform.domain.PageParameter;
import com.greatwall.platform.system.dto.Role;



/**   
 * @Description: 角色类 
 * @author fudk fudk_k@sina.com   
 * @date 2014年7月13日 下午5:50:09    
 */
public interface RoleDao {

	public void saveRoleUser(Role role);
	
	public List<Role> getRoles(Role role);
	
	public List<Role> getRoles(Role role,PageParameter page);
}
