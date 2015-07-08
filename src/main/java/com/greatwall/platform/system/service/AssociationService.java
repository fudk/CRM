package com.greatwall.platform.system.service;

import java.util.List;

import com.greatwall.platform.system.dto.Association;



public interface AssociationService {

	public void saveAssociation(Association association);
	
	public List<Association> getAssociations(Association association);
	
	public void delByKey(int associationId);
	
	public void addOrDelAssociation(Association association);
	
	public void delAssociation(Association association);
}
