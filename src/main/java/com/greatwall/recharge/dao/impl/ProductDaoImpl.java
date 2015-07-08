package com.greatwall.recharge.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.greatwall.platform.base.dao.DaoException;
import com.greatwall.platform.base.dao.MyBatisDao;
import com.greatwall.platform.domain.PageParameter;
import com.greatwall.recharge.dao.ProductDao;
import com.greatwall.recharge.dto.Product;

@Repository
public class ProductDaoImpl extends MyBatisDao implements ProductDao {

	@Override
	public int insert(Product product) {
		return this.save("ProductMapper.insert", product);
	}

	@Override
	public int update(Product product) {
		return this.update("ProductMapper.updateByPrimaryKeySelective", product);
	}
	
	public Product getProduct(Integer productId){
		return this.get("ProductMapper.selectByKey", productId);
	}
	public Product getProduct(Product product){
		return this.get("ProductMapper.selectByCondition", product);
	}

	@Override
	public List<Product> getProducts(Product product, PageParameter page) throws DaoException {
		return this.getListPage("ProductMapper.selectByQueryPage",product,page);
	}

}
