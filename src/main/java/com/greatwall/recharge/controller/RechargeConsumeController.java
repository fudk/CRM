package com.greatwall.recharge.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.greatwall.platform.domain.PageParameter;
import com.greatwall.platform.system.dto.User;
import com.greatwall.platform.system.service.UserService;
import com.greatwall.recharge.dto.Consume;
import com.greatwall.recharge.dto.ConsumeConditions;
import com.greatwall.recharge.dto.Product;
import com.greatwall.recharge.dto.Recharge;
import com.greatwall.recharge.dto.RechargeConditions;
import com.greatwall.recharge.dto.UserChannel;
import com.greatwall.recharge.service.ProductService;
import com.greatwall.recharge.service.RechargeConsumeService;
import com.greatwall.recharge.service.UserChannelService;
import com.greatwall.util.PhoneUtil;
import com.greatwall.util.StringUtil;
import com.greatwall.util.ValidateUtil;

@Controller
@RequestMapping("/rechargeConsume")
public class RechargeConsumeController {

	Logger logger = Logger.getLogger(RechargeConsumeController.class);
	
	@Autowired
	private RechargeConsumeService rechargeConsumeService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private PhoneUtil phoneUtil;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserChannelService userChannelService;
	

	@RequestMapping("/initAddRecharge/{userId}/{loginName}")
	public ModelAndView initAddRecharge(@PathVariable String userId,
			@PathVariable String loginName,ModelMap model){
		model.addAttribute("userId", userId);
		model.addAttribute("loginName", loginName);
		return new ModelAndView("/recharge/addrecharge.jsp");
	}

	@RequestMapping("/addRecharge")
	public@ResponseBody String addRecharge(Recharge recharge,HttpSession session){
		try {
			User u = (User)session.getAttribute("user");
			recharge.setOperator(u.getUserId());
			Integer agentId = null;
			String roleIds = session.getAttribute("roleIds").toString();
			if(!ValidateUtil.isAdmin(roleIds)){//如果是代理商
				agentId = u.getUserId();
			}
			if(rechargeConsumeService.saveRecharge(recharge,agentId)){
				session.setAttribute("user", userService.getUser(u.getUserId()));
				return "success";
			}
			
		} catch (Exception e) {
			logger.error("充值错误",e);
			return "充值失败";
		}
		return "充值失败";
	}
	
	@RequestMapping("/rechargeManage")
	public ModelAndView rechargeManage(ModelMap model){
		
		return new ModelAndView("/recharge/rechargemanage.jsp");
	}

	@RequestMapping("/getRecharges")
	public ModelAndView getRecharges(RechargeConditions rechargeConditions,PageParameter page,HttpSession session,ModelMap model){
		try {
			User u = (User)session.getAttribute("user");
			String roleIds = session.getAttribute("roleIds").toString();
			if(!ValidateUtil.isAdmin(roleIds)){//如果是代理商
				rechargeConditions.setOperator(u.getUserId());
			}
			model.addAttribute("recharges", rechargeConsumeService.getRechargesPage(rechargeConditions, page));
			model.addAttribute("page", page);
		} catch (Exception e) {
			logger.error("查询消费明细错误",e);
			return new ModelAndView("/common/error.jsp");
		}
		
		return new ModelAndView("/recharge/recharges.jsp");
	}

	@RequestMapping("/consumeManage")
	public ModelAndView consumeManage(ModelMap model){
		return new ModelAndView("/recharge/consumemanage.jsp");
	}
	
	@RequestMapping("/getConsumes")
	public ModelAndView getConsumes(ConsumeConditions consume,PageParameter page,HttpSession session,ModelMap model){
		try {
			User u = (User)session.getAttribute("user");
			String roleIds = session.getAttribute("roleIds").toString();
			if(!ValidateUtil.isAdmin(roleIds)){//如果是代理商
				consume.setUserId(u.getUserId());
			}
			model.addAttribute("consumes", rechargeConsumeService.getConsumesPage(consume, page));
			model.addAttribute("page", page);
		} catch (Exception e) {
			logger.error("查询消费明细错误",e);
			return new ModelAndView("/common/error.jsp");
		}
		
		return new ModelAndView("/recharge/consumes.jsp");
	}
	
	@RequestMapping("/addConsumeInit/{productId}")
	public ModelAndView addConsumeInit(@PathVariable Integer productId,ModelMap model){
		
		model.addAttribute("product", productService.getProduct(productId));
		
		return new ModelAndView("/recharge/addconsume.jsp");
	}
	
	@RequestMapping("/addConsume")
	public ModelAndView addConsume(Consume consume,String consumePhones,HttpSession session,ModelMap model){
		try {
			String[] phones = StringUtil.getRepStrings(consumePhones);
			if(ArrayUtils.isNotEmpty(phones)){
				if(consume.getProductId()==null){
					throw new NullPointerException("productId产品ID不能为空");
				}
				Product product = productService.getProduct(consume.getProductId());
				if(product==null){
					throw new NullPointerException("根据productId无法找到对应产品");
				}
				
				consume.setProductName(product.getProductName());
				consume.setProductValue(product.getProductValue());
				consume.setConsumePrice(product.getProductPrice());
				consume.setProductValidity(product.getProductValidity());
				consume.setConsumeNum(1);//默认消费数量为1个
//				consume.setConsumeAmount(MathUtil.mul(product.getProductPrice(), new Double(1), 2));
				consume.setConsumeAmount(product.getProductPrice());
				consume.setConsumeType(product.getProductType());
				consume.setIsp(product.getIsp());
				consume.setState("processing");//处理中
				
				User u = (User)session.getAttribute("user");
				consume.setUserId(u.getUserId());
				
				List<String> errorMsgs = new ArrayList<String>();
				
				for(String phone : phones){
					try {
						//如果电话号码与产品运营商不匹配
						if(!consume.getIsp().toUpperCase().equals(phoneUtil.isPhoneNum(phone))){
							throw new ClassCastException("手机号码与运营商不匹配");
						}
						consume.setConsumePhone(phone);
						
						UserChannel userChannel = new UserChannel();
						userChannel.setUserId(u.getUserId());
						userChannel.setIsp(product.getIsp());
						userChannel.setType(product.getProductType());
						List<UserChannel> uclist = userChannelService.getUserChannel(userChannel);
						String interfaceName = "";
						if(uclist!=null&&uclist.size()>0){
							for(UserChannel uc:uclist){
								if(product.getIsp().equals(uc.getIsp())){
									interfaceName = uc.getInterfaceName();
								}
							}
						}
						if("".equals(interfaceName)){
							throw new ClassCastException("用户未分配通道");
						}
						consume.setInterfaceName(interfaceName);
						
						rechargeConsumeService.addConsume(consume);
						
					}catch (ClassCastException e) {
						logger.error(phone+" "+e.getMessage());
						errorMsgs.add(phone+" "+e.getMessage());
					}catch (Exception e) {
						logger.error(phone+" 消费错误 ",e);
						errorMsgs.add(phone+" "+e.getMessage());
					}
				}
				
				session.setAttribute("user", userService.getUser(u.getUserId()));
				
				if(errorMsgs!=null&&errorMsgs.size()>0){
					model.addAttribute("errorMsgs", errorMsgs);
					model.addAttribute("msg", phones.length-errorMsgs.size()+"保存成功，"+errorMsgs.size()+"保存失败。成功操作可在消费记录中查询");
				}else{
					model.addAttribute("msg", "success");
				}
				
			}else{
				model.addAttribute("msg", "无有效的手机号");
			}
		} catch (Exception e) {
			logger.error("消费错误",e);
			model.addAttribute("msg", "提交错误！"+e.getMessage());
		}
		return new ModelAndView("/recharge/consumereturn.jsp");
		
	}
}
