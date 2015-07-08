package com.greatwall.platform.system.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.greatwall.platform.base.dao.DaoException;
import com.greatwall.platform.base.dao.MyBatisDao;
import com.greatwall.platform.domain.PageParameter;
import com.greatwall.platform.system.dao.RoleDao;
import com.greatwall.platform.system.dto.Role;

@Repository
public class RoleDaoImpl extends MyBatisDao implements RoleDao {

	private static Logger logger = Logger.getLogger(RoleDaoImpl.class);
			
	public void saveRole(Role role){
		this.save("roleMapper.insertRole", role);
	}
	
	public void saveRoleUser(Role role){
		this.save("roleMapper.insertRoleUser", role);
	}
	
	public List<Role> getRoles(Role role){
		return this.getList("roleMapper.getRoles", role);
	}
	
	public List<Role> getRoles(Role role,PageParameter page){
		try {
			return this.getListPage("roleMapper.getRolesPage", role,page);
		} catch (DaoException e) {
			logger.error(e);
			return null;
		}
	}
	
}
