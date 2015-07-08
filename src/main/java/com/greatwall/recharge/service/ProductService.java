package com.greatwall.recharge.service;

import java.util.List;

import com.greatwall.platform.base.dao.DaoException;
import com.greatwall.platform.domain.PageParameter;
import com.greatwall.recharge.dto.Product;


public interface ProductService {

	public Boolean addProduct(Product product) throws DaoException;
	
	public Boolean updateProduct(Product product) throws DaoException;
	
	public Product getProduct(Integer productId);
	
	public Product getProduct(Product product);
	
	public List<Product> getProductsPage(Product product,PageParameter page) throws DaoException;
	
}
