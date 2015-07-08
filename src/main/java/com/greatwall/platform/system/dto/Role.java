package com.greatwall.platform.system.dto;



/**   
 * @Description: 角色类 
 * @author fudk fudk_k@sina.com   
 * @date 2014年7月13日 下午3:47:14    
 */
public class Role {

	/**
	 * @Fields roleId : 角色id
	 */
	private Integer roleId; 
	/**
	 * @Fields userId : 用户id
	 */
	private Integer userId; 
	/**
	 * @Fields roleName : 角色名称
	 */
	private String roleName;
	
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	} 
	
	
	
}
