package com.greatwall.platform.system.dao;


import java.util.List;

import com.greatwall.platform.base.dao.DaoException;
import com.greatwall.platform.domain.PageParameter;
import com.greatwall.platform.system.dto.Log;

public interface LogDao {

	public void saveLog(Log log);

	List<Log> getLogs(Log log, PageParameter page) throws DaoException;
		
}
