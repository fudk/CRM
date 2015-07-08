package com.greatwall.platform.system.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.greatwall.platform.base.dao.DaoException;
import com.greatwall.platform.domain.PageParameter;
import com.greatwall.platform.system.dto.User;
import com.greatwall.platform.system.service.RoleService;
import com.greatwall.platform.system.service.UserService;
import com.greatwall.recharge.dto.ChannelCondition;
import com.greatwall.recharge.dto.UserChannel;
import com.greatwall.recharge.service.ChannelService;
import com.greatwall.recharge.service.UserChannelService;
import com.greatwall.util.RMSConstant;
import com.greatwall.util.ValidateUtil;

@Controller
@RequestMapping("/system/user")
public class UserController {
	
	Logger logger = Logger.getLogger(UserController.class);

	@Autowired
	private UserService userService;
	
	@Autowired
	private ChannelService channelService;
	
	@Autowired
	private UserChannelService userChannelService;
	@Autowired
	private RoleService roleService;
	
	
	@RequestMapping("/userManage")
	public ModelAndView userManage(User user,ModelMap model){
		//model.addAttribute("roletree", JSONArray.fromObject(roleService.getRoleTree()));
		return new ModelAndView("/sysmanage/user/usermanage.jsp");
	}
	@RequestMapping("/getUsers")
	public ModelAndView getUsers(User user,PageParameter page,HttpSession session,ModelMap model){
		try {
			String roleIds = session.getAttribute("roleIds").toString();
			if(ValidateUtil.isAgent(roleIds)){
				User u = (User)session.getAttribute("user");
				user.setParentId(u.getUserId());
			}
			
			model.addAttribute("users", userService.getUsers(user, page));
			model.addAttribute("page", page);
		} catch (DaoException e) {
			logger.error("查询用户列表错误",e);
			return new ModelAndView("/common/error.jsp");
		}
		return new ModelAndView("/sysmanage/user/users.jsp");
	}
	@RequestMapping("/searchUsers")
	public @ResponseBody List<User> searchUsers(String q,PageParameter page,HttpSession session) throws DaoException{
		if(StringUtils.isBlank(q)){
			return null;
		}
		User user = new User();
		user.setLoginName(q.trim());
		String roleIds = session.getAttribute("roleIds").toString();
		if(ValidateUtil.isAgent(roleIds)){
			User u = (User)session.getAttribute("user");
			user.setParentId(u.getUserId());
		}
		return userService.searchUsers(user, page);
		/*List<User> userlist = userService.searchUsers(user, page);
		List<Map<String,Object>> relist = new ArrayList<Map<String,Object>>();
		if(userlist!=null&&userlist.size()>0){
			for(User us : userlist){
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("label", us.getLoginName());
				map.put("value", us.getUserId());
				map.put("userName", us.getUserName());
				relist.add(map);
			}
		}
		
		return relist;*/
	}
	
	/** 
	* @Title: addUserInit 
	* @Description: 显示新增用户页面 
	* @param @param model
	* @param @return    设定文件 
	* @return ModelAndView    返回类型 
	* @throws 
	*/
	@RequestMapping("/addUserInit")
	public ModelAndView addUserInit(ModelMap model){
		model.addAttribute(RMSConstant.ISP_CM, channelService.getChannelsIsp(RMSConstant.ISP_CM,RMSConstant.PRODUCT_TYPE_PHONE));
		model.addAttribute(RMSConstant.ISP_CU, channelService.getChannelsIsp(RMSConstant.ISP_CU,RMSConstant.PRODUCT_TYPE_PHONE));
		model.addAttribute(RMSConstant.ISP_CT, channelService.getChannelsIsp(RMSConstant.ISP_CT,RMSConstant.PRODUCT_TYPE_PHONE));
		model.addAttribute(RMSConstant.ISP_CM+"flow", channelService.getChannelsIsp(RMSConstant.ISP_CM,RMSConstant.PRODUCT_TYPE_FLOW));
		model.addAttribute(RMSConstant.ISP_CU+"flow", channelService.getChannelsIsp(RMSConstant.ISP_CU,RMSConstant.PRODUCT_TYPE_FLOW));
		model.addAttribute(RMSConstant.ISP_CT+"flow", channelService.getChannelsIsp(RMSConstant.ISP_CT,RMSConstant.PRODUCT_TYPE_FLOW));
		
		return new ModelAndView("/sysmanage/user/regist.jsp");
	}
	
	/** 
	* @Title: addUser 
	* @Description: 新增用户，并制定用户运营商对应的通道 
	* @param user
	* @param cm	移动对应通道id值
	* @param cu 联通对应通道id值
	* @param ct 电信对应通道id值
	* @param model
	* @return    设定文件
	* @return String    返回类型 
	* @throws 
	*/
	@RequestMapping("/addUser")
	public@ResponseBody String addUser(User user,ChannelCondition channelCondition,Integer roleId,
			HttpSession session,ModelMap model){
		try {
			if(!userService.hasUser(user)){
				User u = (User)session.getAttribute("user");
				user.setParentId(u.getUserId());
				user.setDiscountFlow(100);
				user.setDiscountPhone(100);
				
				//如果是代理商新增普通用户，用户的通道与创建代理商通道默认一致。
				String roleIds = session.getAttribute("roleIds").toString();
				if(ValidateUtil.isAgent(roleIds)){//不是管理员，是代理商,1是管理员角色ID，2是代理商角色ID
					UserChannel userChannel = new UserChannel();
					userChannel.setUserId(u.getUserId());
					List<UserChannel> uclist = userChannelService.getUserChannel(userChannel);
					channelCondition = UserChannelListToChannelCondition(uclist);
				}
				
				userService.saveUserAndChannel(user,channelCondition,roleId);
			}else{
				return "平台用户ID或登录名(手机)重复";
			}
		} catch (Exception e) {
			logger.error("保存用户失败",e);
			return e.getMessage();
		}
		return "success";
	}
	
	private ChannelCondition UserChannelListToChannelCondition(List<UserChannel> uclist){
		ChannelCondition channelCondition = new ChannelCondition();
		if(uclist!=null){
			for(UserChannel uc:uclist){
				if(RMSConstant.PRODUCT_TYPE_PHONE.equals(uc.getType())){
					if(RMSConstant.ISP_CM.equals(uc.getIsp())){
						channelCondition.setCm(uc.getChannelId());
					}else if(RMSConstant.ISP_CU.equals(uc.getIsp())){
						channelCondition.setCu(uc.getChannelId());
					}else if(RMSConstant.ISP_CT.equals(uc.getIsp())){
						channelCondition.setCt(uc.getChannelId());
					}
				}else if(RMSConstant.PRODUCT_TYPE_FLOW.equals(uc.getType())){
					if(RMSConstant.ISP_CM.equals(uc.getIsp())){
						channelCondition.setCmflow(uc.getChannelId());
					}else if(RMSConstant.ISP_CU.equals(uc.getIsp())){
						channelCondition.setCuflow(uc.getChannelId());
					}else if(RMSConstant.ISP_CT.equals(uc.getIsp())){
						channelCondition.setCtflow(uc.getChannelId());
					}
				}
			}
		}
		return channelCondition;
	}
	
	@RequestMapping("/updateUserInit/{userId}")
	public ModelAndView updateUserInit(@PathVariable Integer userId,ModelMap model){
		model.addAttribute(RMSConstant.ISP_CM, channelService.getChannelsIsp(RMSConstant.ISP_CM,RMSConstant.PRODUCT_TYPE_PHONE));
		model.addAttribute(RMSConstant.ISP_CU, channelService.getChannelsIsp(RMSConstant.ISP_CU,RMSConstant.PRODUCT_TYPE_PHONE));
		model.addAttribute(RMSConstant.ISP_CT, channelService.getChannelsIsp(RMSConstant.ISP_CT,RMSConstant.PRODUCT_TYPE_PHONE));
		model.addAttribute(RMSConstant.ISP_CM+"flow", channelService.getChannelsIsp(RMSConstant.ISP_CM,RMSConstant.PRODUCT_TYPE_FLOW));
		model.addAttribute(RMSConstant.ISP_CU+"flow", channelService.getChannelsIsp(RMSConstant.ISP_CU,RMSConstant.PRODUCT_TYPE_FLOW));
		model.addAttribute(RMSConstant.ISP_CT+"flow", channelService.getChannelsIsp(RMSConstant.ISP_CT,RMSConstant.PRODUCT_TYPE_FLOW));
		
		
		UserChannel userChannel = new UserChannel();
		userChannel.setUserId(userId);
		List<UserChannel> uclist = userChannelService.getUserChannel(userChannel);
		
		Gson gson = new Gson();
		model.addAttribute("isp", gson.toJson(uclist));
		
		model.addAttribute("updateUser", userService.getUser(userId));
		return new ModelAndView("/sysmanage/user/regist.jsp");
	}
	
	@RequestMapping("/updateUser")
	public@ResponseBody String updateUser(User user,ChannelCondition channelCondition,HttpSession session,ModelMap model){
		try {
			user.setDiscountFlow(100);
			user.setDiscountPhone(100);
			userService.updateUserAndChannel(user,channelCondition);
		} catch (Exception e) {
			logger.error("保存用户失败",e);
			return e.getMessage();
		}
		return "success";
	}
	
	public@ResponseBody String disableUser(){
		
		return "success";
	}
	public@ResponseBody String enableUser(){
		
		return "success";
	}
	
	@RequestMapping("/passwordInit")
	public ModelAndView changePasswordInit(){
		return new ModelAndView("/sysmanage/user/changepassword.jsp");
	}
	
	@RequestMapping("/updatePassword")
	public@ResponseBody String updatePassword(String oldPassword,String newPassword,HttpSession session){
		User u = (User)session.getAttribute("user");
		
		if(userService.updatePassowrd(u.getUserId(), oldPassword, newPassword)){
			return "success";
		}else{
			return "老密码错误";
		}
		
		
	}
}
