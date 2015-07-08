package com.greatwall.platform.system.dao;

import java.util.List;

import com.greatwall.platform.system.dto.Association;


public interface AssociationDao {

	public void saveAssociation(Association association);
	
	public List<Association> selectAssociations(Association association);
	
	public void deleteByPrimaryKey(int associationId);
	
	public Association selectAssociation(Association association);
}
