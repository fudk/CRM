package com.greatwall.recharge.dao;

import java.util.List;

import com.greatwall.platform.base.dao.DaoException;
import com.greatwall.platform.domain.PageParameter;
import com.greatwall.recharge.dto.Product;

public interface ProductDao {

	public int insert(Product product);
	
	public int update(Product product);
	
	public Product getProduct(Integer productId);
	
	public Product getProduct(Product product);
	
	public List<Product> getProducts(Product product,PageParameter page) throws DaoException;
}
