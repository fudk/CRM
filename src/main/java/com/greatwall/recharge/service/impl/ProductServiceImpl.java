package com.greatwall.recharge.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.greatwall.platform.base.dao.DaoException;
import com.greatwall.platform.domain.PageParameter;
import com.greatwall.recharge.dao.ProductDao;
import com.greatwall.recharge.dto.Product;
import com.greatwall.recharge.service.ProductService;

@Service("productService")
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDao productDao;
	
	@Override
	public Boolean addProduct(Product product) throws DaoException {
		if(productDao.insert(product) ==1 ){
			return true;
		}else{
			return false;
		}
	}
	
	
	public Boolean updateProduct(Product product) throws DaoException {
		if(productDao.update(product) ==1 ){
			return true;
		}else{
			return false;
		}
	}
	
	public Product getProduct(Integer productId){
		return productDao.getProduct(productId);
	}
	
	public Product getProduct(Product product){
		return productDao.getProduct(product);
	}

	@Override
	public List<Product> getProductsPage(Product product, PageParameter page)
			throws DaoException {
		return productDao.getProducts(product, page);
	}

}
