package com.greatwall.recharge.service;

import java.util.List;

import com.greatwall.platform.base.dao.DaoException;
import com.greatwall.platform.domain.PageParameter;
import com.greatwall.recharge.dto.Channel;
import com.greatwall.recharge.dto.Notice;


public interface NoticeService {

	int deleteNotice(Integer id);

	int saveNotice(Notice notice);

	int updateNotice(Notice notice);

	List<Notice> getNotices(Notice notice, PageParameter page)
			throws DaoException;

	Notice getNotice(Integer id);
}
