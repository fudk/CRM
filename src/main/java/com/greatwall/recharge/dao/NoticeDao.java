package com.greatwall.recharge.dao;

import java.util.List;

import com.greatwall.platform.base.dao.DaoException;
import com.greatwall.platform.domain.PageParameter;
import com.greatwall.recharge.dto.Notice;


public interface NoticeDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Notice notice);

    Notice selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Notice notice);

	List<Notice> getNotices(Notice notice, PageParameter page)
			throws DaoException;

}