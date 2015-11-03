package com.greatwall.recharge.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.greatwall.platform.base.dao.DaoException;
import com.greatwall.platform.base.dao.MyBatisDao;
import com.greatwall.platform.domain.PageParameter;
import com.greatwall.recharge.dao.NoticeDao;
import com.greatwall.recharge.dto.Notice;

@Repository
public class NoticeDaoImpl extends MyBatisDao implements NoticeDao {

			
	/*public int insert(Channel channel) {
		return this.save("ChannelMapper.insert",channel);
	}
	
	public List<Channel> getChannelsIsp(String isp,String type){
		Map<String,String> params = new HashMap<String,String>();
		params.put("isp", "%"+isp+"%");
		params.put("type", type);
		return this.getList("ChannelMapper.getChannelsIsp", params);
	}*/

	@Override
	public int deleteByPrimaryKey(Integer id) {
		return this.delete("NoticeMapper.deleteByPrimaryKey", id);
	}

	@Override
	public int insert(Notice notice) {
		return this.save("NoticeMapper.insert", notice);
	}

	@Override
	public Notice selectByPrimaryKey(Integer id) {
		return this.get("NoticeMapper.selectByPrimaryKey", id);
	}

	@Override
	public int updateByPrimaryKeySelective(Notice notice) {
		return this.update("NoticeMapper.updateByPrimaryKeySelective", notice);
	}
	
	@Override
	public List<Notice> getNotices(Notice notice, PageParameter page) throws DaoException {
		return this.getListPage("NoticeMapper.selectPage",notice,page);
	}

}
