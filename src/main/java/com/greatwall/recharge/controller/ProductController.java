package com.greatwall.recharge.controller;

import java.io.File;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.greatwall.platform.domain.PageParameter;
import com.greatwall.platform.system.dto.User;
import com.greatwall.recharge.dto.Product;
import com.greatwall.recharge.service.ProductService;

@Controller
@RequestMapping("/product")
public class ProductController {

	Logger logger = Logger.getLogger(ProductController.class);
	
	@Autowired
	private ProductService productService;
	
	private String imgPath;
	
	@Value("#{propertiesReader['sys.imgPath']}") 
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	@RequestMapping("/addProductInit")
	public ModelAndView productAddInit(ModelMap model){
		return new ModelAndView("/recharge/addProduct.jsp");
	}
	
	@RequestMapping("/addProduct")
	public ModelAndView addProduct(Product product,@RequestParam MultipartFile imgfile,HttpSession session,ModelMap model){
		try {
			if(product!=null){
		        product.setCreateTime(new Date());
		        product.setProductImg(this.getProductImgPath(imgfile, session));
			}
			if(!productService.addProduct(product)){
				model.addAttribute("msg", "未保存记录");
			}else{
				model.addAttribute("msg", "success");
			}
		} catch (Exception e) {
			logger.error("新增产品错误",e);
			model.addAttribute("msg", "新增产品错误!"+e.getMessage());
		}
		return new ModelAndView("/recharge/return.jsp");
	}
	
	@RequestMapping("/updateProductInit/{productId}")
	public ModelAndView updateProductInit(@PathVariable Integer productId,ModelMap model){
		
		model.addAttribute("product", productService.getProduct(productId));
		return new ModelAndView("/recharge/addProduct.jsp");
	}
	
	@RequestMapping("/updateProduct")
	public ModelAndView updateProduct(Product product,@RequestParam MultipartFile imgfile,HttpSession session,ModelMap model){
		try {
			if(product==null||product.getProductId()==null){
				throw new NullPointerException("productId产品ID不能为空");
			}
			
			if(imgfile!=null&&imgfile.getSize()>0){
				product.setProductImg(this.getProductImgPath(imgfile, session));
			}
			if(!productService.updateProduct(product)){
				model.addAttribute("msg", "未保存记录");
			}else{
				model.addAttribute("msg", "success");
			}
		} catch (Exception e) {
			logger.error("新增产品错误",e);
			model.addAttribute("msg", "新增产品错误!"+e.getMessage());
		}
		return new ModelAndView("/recharge/return.jsp");
	}
	
	
	
	/** 
	* @Title: getProductImgPath 
	* @Description: 保存图片，并返回保存地址
	* @param imgfile
	* @param session
	* @return    设定文件
	* @return String    返回类型 
	* @throws 
	*/
	private String getProductImgPath(MultipartFile imgfile,HttpSession session){
//		String path = session.getServletContext().getRealPath("upload");  
		
        String fileName = imgfile.getOriginalFilename();  
        fileName = new Date().getTime()+fileName.substring(fileName.indexOf("."), fileName.length());  
        File targetFile = new File(imgPath, fileName);  
        if(!targetFile.exists()){  
            targetFile.mkdirs();  
        }  
        //保存  
        try {  
        	imgfile.transferTo(targetFile);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return fileName;
	}
	
	@RequestMapping("/productManage")
	public ModelAndView productManage(Product product,ModelMap model){
		//model.addAttribute("roletree", JSONArray.fromObject(roleService.getRoleTree()));
		return new ModelAndView("/recharge/productmanage.jsp");
	}
	
	@RequestMapping("/getProducts")
	public ModelAndView getProducts(Product product,PageParameter page,ModelMap model){
		try {
			model.addAttribute("products",productService.getProductsPage(product, page));
			model.addAttribute("page", page);
		} catch (Exception e) {
			logger.error("查询产品列表错误",e);
			return new ModelAndView("/common/error.jsp");
		}
		return new ModelAndView("/recharge/products.jsp");
	}
	@RequestMapping("/getProductsMain")
	public ModelAndView getProductsMain(Product product,PageParameter page,ModelMap model){
		try {
			product.setState("enable");
			model.addAttribute("products",productService.getProductsPage(product, page));
			model.addAttribute("page", page);
		} catch (Exception e) {
			logger.error("查询产品列表错误",e);
			return new ModelAndView("/common/error.jsp");
		}
		return new ModelAndView("/recharge/productsMain.jsp");
	}
}
