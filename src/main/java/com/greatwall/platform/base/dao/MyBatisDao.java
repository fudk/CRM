package com.greatwall.platform.base.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.greatwall.platform.domain.PageParameter;


/**
 * MyBatis的Dao基类
 * @author fudk_k@sina.com
 * @update 2014-7-5
 */
public class MyBatisDao extends SqlSessionDaoSupport{

	public int save(String key, Object object) {
		return getSqlSession().insert(key, object);
	}

	public int update(String key, Object object) {
		return getSqlSession().update(key, object);
	}

	public int delete(String key, Serializable id) {
		return getSqlSession().delete(key, id);
	}

	public int delete(String key, Object object) {
		return getSqlSession().delete(key, object);
	}

	public <T> T get(String key, Object params) {
		return getSqlSession().selectOne(key, params);
	}

	public <T> List<T> getList(String key) {
		return getSqlSession().selectList(key);
	}

	public <T> List<T> getList(String key, Object params) {
		return getSqlSession().selectList(key, params);
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> getListPage(String key, Object params,PageParameter page) throws DaoException {
		try {
			@SuppressWarnings("rawtypes")
			Map paramMap = PropertyUtils.describe(params);
			paramMap.put("page", page);
			return getSqlSession().selectList(key, paramMap);
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}


}
