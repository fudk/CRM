package com.greatwall.recharge.controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.greatwall.api.service.CallbackNotifyService;
import com.greatwall.clientapi.service.ClientService;
import com.greatwall.platform.base.dao.DaoException;
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
import com.greatwall.util.RMSConstant;
import com.greatwall.util.StringUtil;
import com.greatwall.util.ValidateUtil;

@Controller
@RequestMapping("/rechargeConsume")
public class RechargeConsumeController {

	Logger logger = Logger.getLogger(RechargeConsumeController.class);
	
	ExecutorService fixedThreadPool = Executors.newFixedThreadPool(2);

	private String basePath;

	@Value("#{propertiesReader['sys.imgPath']}") 
	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}
	
	@Autowired
	private CallbackNotifyService callbackNotifyService;

	@Autowired
	private RechargeConsumeService rechargeConsumeService;
	
	@Autowired
	private ClientService clientService;

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
	@RequestMapping("/showSendFile")
	public ModelAndView showSendFile(String path,ModelMap model){
		ModelAndView mv = new ModelAndView("/recharge/showsendfile.jsp");
		if(path==null||!"".equals(path)){
			return mv;
		}
		model.addAttribute("tels", getSendTelList(path));
		return mv;
	}
	
	private List<String> getSendTelList(String path){
		List<String> tels = new ArrayList<>();
		BufferedReader br = null;
		FileReader reader = null;
		try {
			reader = new FileReader(basePath+path);
			br = new BufferedReader(reader);  
			String data = br.readLine();//一次读入一行，直到读入null为文件结束  
			while( data!=null){  
				tels.add(data);
				data = br.readLine(); //接着读下一行  
			}
		} catch (FileNotFoundException e) {
			logger.error("path "+basePath+path+" 文件不存在");
		} catch (IOException e) {
			logger.error("path "+path+" 读取错误");
		}finally{
			try {
				if(br!=null){
					br.close();
				}
				if(reader!=null){
					reader.close();
				}
			} catch (IOException e) {
			}
		}
		return tels;
	}

	@RequestMapping("/addConsumes")
	public@ResponseBody Map<String,Object> addConsumes(String type,String tel,String sendfile,String productValue,HttpSession session){
		Map<String,Object> map = new HashMap<String,Object>(); 
		if(type==null){
			map.put("msg","充值类型不能为空");
			return map;
		}
		Product product = new Product();
		product.setProductType(type);
		product.setProductValue("flow".equals(type)?productValue+"M":productValue);
		product.setState("enable");
		PageParameter page = new PageParameter();
		try {
			User u = (User)session.getAttribute("user");
			
			List<Product> products = productService.getProductsPage(product,page);
			
			
			UserChannel userChannel = new UserChannel();
			userChannel.setUserId(u.getUserId());
			userChannel.setType(product.getProductType());
			List<UserChannel> uclist = userChannelService.getUserChannel(userChannel);
			
			if(tel!=null){
				String reStr = sendConsume(u.getUserId(),tel,uclist,products);
				if(!"success".equals(reStr)){
					map.put("msg",reStr);
					return map;
				}else{
					map.put("msg","success");
				}
			}else{
				List<String> errorMsgs = new ArrayList<String>();
				List<String> tels = getSendTelList(sendfile);
				if(tels!=null&&tels.size()>0){
					for(String phone:tels){
						try {
							String reStr = sendConsume(u.getUserId(),phone,uclist,products);
							if(!"success".equals(reStr)){
								errorMsgs.add(phone+" "+reStr);
							}
						} catch (Exception e) {
							errorMsgs.add(phone+" "+e.getMessage());
						}
					}
				}
				
				if(errorMsgs!=null&&errorMsgs.size()>0){
					map.put("errorMsgs", errorMsgs);
					map.put("msg", tels.size()-errorMsgs.size()+"保存成功，"+errorMsgs.size()+"保存失败。成功操作可在消费记录中查询");
				}else{
					map.put("msg", "success");
				}
			}
			User user = userService.getUser(u.getUserId());
			session.setAttribute("user", user);
			map.put("amount", user.getBalance()-u.getBalance());
			
		} catch (Exception e) {
			logger.error("", e);
			map.put("msg",e.getMessage());
			return map;
		}
		return map;
	}
	
	
	private String sendConsume(Integer userId,String tel,List<UserChannel> uclist,List<Product> products) throws Exception{
		Product product = null;
		String isp = phoneUtil.isPhoneNum(tel);
		for(Product pro:products){
			if(pro.getIsp().toUpperCase().equals(isp)){
				product = pro;
				System.out.println(pro.getProductName());
			}
		}
		if(product==null){
			return phoneUtil.getIspName(isp)+" 无此号码对应产品";
		}
		
		Consume consume = new Consume();
		
		consume.setProductId(product.getProductId());
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
		consume.setDiscount(RMSConstant.DEFAULT_DISCOUNT);//默认折扣

		consume.setUserId(userId);
		consume.setConsumePhone(tel);
		
		String interfaceName = "";
		if(uclist!=null&&uclist.size()>0){
			for(UserChannel uc:uclist){
				if(product.getIsp().equals(uc.getIsp())){
					interfaceName = uc.getInterfaceName();
					consume.setDiscount(uc.getDiscount());
				}
			}
		}
		if("".equals(interfaceName)){
			return "用户未分配通道";
//			throw new ClassCastException("用户未分配通道");
		}
		consume.setInterfaceName(interfaceName);

		rechargeConsumeService.addConsume(consume);
		
		return "success";
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
				consume.setDiscount(RMSConstant.DEFAULT_DISCOUNT);//默认折扣

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
									consume.setDiscount(uc.getDiscount());
								}
							}
						}
						if("".equals(interfaceName)){
							throw new ClassCastException("用户未分配通道");
						}else if(RMSConstant.INTERFACE_NAME_QIUTONG.equals(interfaceName)){
							consume.setProductParam(product.getQtProductId());
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
	
	@RequestMapping("/changeState")
	public@ResponseBody String changeState(String consumeId,String state,HttpSession session){
		String roleIds = session.getAttribute("roleIds").toString();
		if(!ValidateUtil.isAdmin(roleIds)){
			return "不是管理员，不能操作";
		}
		if(!RMSConstant.CONSUME_STATE_SENDED_FAIL.equals(state)&&!RMSConstant.CONSUME_STATE_SUC.equals(state)){
			return "状态错误！";
		}
		
		Consume consu = new Consume();
		consu.setConsumeId(consumeId);
		ConsumeConditions cons = rechargeConsumeService.getConsumeConditions(consu);
		
		try {
			if(cons==null){
				return "充值记录为空";
			}
			if(!cons.getState().equals("sended")&&!cons.getState().equals("sended_processing")
					&&!cons.getState().equals("sended_wait")&&!cons.getState().equals("s_error")
					&&!cons.getState().equals("error")){
				return "只有充值中的和充值错误的才能调整状态";
			}
			if(RMSConstant.CONSUME_STATE_SENDED_FAIL.equals(state)){
				state = RMSConstant.CONSUME_STATE_FAIL;
				clientService.rechargeReturn(cons,state);
				run(fixedThreadPool,cons,"00");
			}else{
				rechargeConsumeService.confirmConsume(cons.getConsumeId(), state);
				run(fixedThreadPool,cons,"01");
			}
			return "success";
		} catch (Exception e) {
			logger.error("", e);
			return "程序错误";
		}
	}
	
	private void run(ExecutorService threadPool,final ConsumeConditions consumeConditions,final String opstatus) {
		threadPool.execute(new Runnable() {  
			@Override
			public void run() {  
				try {  
					callbackNotifyService.callbackNotify(consumeConditions, opstatus);
				} catch (Exception e) {  
					logger.error("充值状态回调错误 : "+consumeConditions.getNotifyUrl(), e);
				}finally{
					
				}
			}  
		});  
		//threadPool.shutdown();// 任务执行完毕，关闭线程池  
	}
	
}
