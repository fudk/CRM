package com.greatwall.platform.system.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.greatwall.platform.base.dao.MyBatisDao;
import com.greatwall.platform.system.dao.AssociationDao;
import com.greatwall.platform.system.dto.Association;

/**   
 * @Description: 关联关系 
 * @author fudk fudk_k@sina.com   
 * @date 2014年8月2日 下午5:13:22    
 */
@Repository
public class AssociationDaoImpl extends MyBatisDao implements AssociationDao{

	public void saveAssociation(Association association){
		this.save("associationMapper.insert", association);
	}
	
	public List<Association> selectAssociations(Association association){
		return this.getList("associationMapper.selectAssociations", association);
	}
	
	public Association selectAssociation(Association association){
		return this.get("associationMapper.selectAssociations", association);
	}
	
	public void deleteByPrimaryKey(int associationId){
		this.delete("associationMapper.deleteByPrimaryKey", associationId);
	}
	
}
