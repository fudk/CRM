package com.greatwall.platform.system.dto;

/**   
 * @Description: 菜单资源列表 
 * @author fudk fudk_k@sina.com   
 * @date 2014年7月15日 下午10:20:37    
 */
public class Resource {

	/**
	 * @Fields resId : 菜单资源id
	 */
	private Integer resId;
	/**
	 * @Fields resName : 菜单资源名称
	 */
	private String resName;
	/**
	 * @Fields resUrl : 菜单资源url
	 */
	private String resUrl;
	/**
	 * @Fields resOrder : 资源排序
	 */
	private Integer resOrder;
	/**
	 * @Fields dataAccess : 数据权限
	 */
	private String dataAccess;
	/**
	 * @Fields parentId : 父节点
	 */
	private Integer parentId;
	/**
	 * @Fields level : 树层级
	 */
	private Integer level;
	private String resType;
	
	public Integer getResId() {
		return resId;
	}
	public void setResId(Integer resId) {
		this.resId = resId;
	}
	public String getResName() {
		return resName;
	}
	public void setResName(String resName) {
		this.resName = resName;
	}
	public String getResUrl() {
		return resUrl;
	}
	public void setResUrl(String resUrl) {
		this.resUrl = resUrl;
	}
	public Integer getResOrder() {
		return resOrder;
	}
	public void setResOrder(Integer resOrder) {
		this.resOrder = resOrder;
	}
	public String getDataAccess() {
		return dataAccess;
	}
	public void setDataAccess(String dataAccess) {
		this.dataAccess = dataAccess;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public String getResType() {
		return resType;
	}
	public void setResType(String resType) {
		this.resType = resType;
	}
	public boolean equals(Object obj) {
		if(obj==null)
			return false;
		if(this == obj){
			return true;
		}
		if (obj instanceof Resource) {
			Resource other = (Resource) obj;
			return  (other.resId).equals(this.resId);
		}
		return false;
	}
	public int hashCode() {
		int result = getResName().hashCode();
		result = 29 * result +getResUrl().hashCode();
		return result;
	}
	
}
