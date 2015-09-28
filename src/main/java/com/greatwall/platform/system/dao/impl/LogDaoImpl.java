package com.greatwall.platform.system.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.greatwall.platform.base.dao.DaoException;
import com.greatwall.platform.base.dao.MyBatisDao;
import com.greatwall.platform.domain.PageParameter;
import com.greatwall.platform.system.dao.LogDao;
import com.greatwall.platform.system.dto.Log;


/**   
 * @Description: 日志处理 
 * @author fudk fudk_k@sina.com   
 * @date 2014年8月24日 下午6:02:59    
 */
@Repository
public class LogDaoImpl extends MyBatisDao implements LogDao {

	public void saveLog(Log log){
		this.save("LogMapper.insert", log);
	}
	
	@Override
	public List<Log> getLogs(Log log,PageParameter page) throws DaoException{
		return this.getListPage("LogMapper.selectPage",log,page);
	}
	
	/*public Date getLastSynDate(Log log){
		return this.get("LogMapper.getLastSynDate", log);
		List<Log> loglist = this.getList("LogMapper.getLogs", log);
		if(loglist!=null&&loglist.size()>0){
			return loglist.get(0).getEndDate();
		}
		return null;
	}*/
			
}
