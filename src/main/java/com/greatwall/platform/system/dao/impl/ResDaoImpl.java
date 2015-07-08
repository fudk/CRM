package com.greatwall.platform.system.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.greatwall.platform.base.dao.MyBatisDao;
import com.greatwall.platform.system.dao.ResDao;
import com.greatwall.platform.system.dto.Res;

/**   
 * @Description: 系统资源持久层 
 * @author fudk fudk_k@sina.com   
 * @date 2014年7月14日 下午9:49:36    
 */
@Repository
public class ResDaoImpl extends MyBatisDao implements ResDao {

	public List<Res> getReses(Res res){
		return this.getList("resMapper.getRes", res);
	}
	
	public void saveRes(Res res){
		this.save("userMapper.insertUser",res);
	}
}
