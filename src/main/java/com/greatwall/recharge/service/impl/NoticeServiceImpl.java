package com.greatwall.recharge.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.greatwall.platform.base.dao.DaoException;
import com.greatwall.platform.domain.PageParameter;
import com.greatwall.recharge.dao.NoticeDao;
import com.greatwall.recharge.dto.Notice;
import com.greatwall.recharge.service.NoticeService;

@Service("noticeService")
public class NoticeServiceImpl implements NoticeService  {

	@Autowired
	private NoticeDao noticeDao;

	@Override
	public int deleteNotice(Integer id) {
		return noticeDao.deleteByPrimaryKey(id);
	}

	@Override
	public int saveNotice(Notice notice) {
		return noticeDao.insert(notice);
	}

	@Override
	public Notice getNotice(Integer id) {
		return noticeDao.selectByPrimaryKey(id);
	}

	@Override
	public int updateNotice(Notice notice) {
		return noticeDao.updateByPrimaryKeySelective(notice);
	}

	@Override
	public List<Notice> getNotices(Notice notice, PageParameter page)
			throws DaoException {
		return noticeDao.getNotices(notice, page);
	}
	
}
