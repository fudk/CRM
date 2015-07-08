package com.greatwall.platform.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.greatwall.platform.system.service.AssociationService;
import com.greatwall.platform.system.dao.AssociationDao;
import com.greatwall.platform.system.dto.Association;

@Service("associationService")
public class AssociationServiceImpl implements AssociationService {

	@Autowired
	private AssociationDao associationDao;
	
	public void saveAssociation(Association association){
		associationDao.saveAssociation(association);
	}
	
	public void addOrDelAssociation(Association association){
		Association ass = associationDao.selectAssociation(association);
		if(ass==null){
			associationDao.saveAssociation(association);
		}else{
			associationDao.deleteByPrimaryKey(ass.getAssociationId());
		}
	}
	
	public List<Association> getAssociations(Association association){
		return associationDao.selectAssociations(association);
	}
	
	public void delByKey(int associationId){
		associationDao.deleteByPrimaryKey(associationId);
	}
	public void delAssociation(Association association){
		Association ass = associationDao.selectAssociation(association);
		if(ass!=null){
			associationDao.deleteByPrimaryKey(ass.getAssociationId());
		}
	}
}
