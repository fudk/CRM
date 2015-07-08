package com.greatwall.platform.system.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.greatwall.platform.domain.PageParameter;
import com.greatwall.platform.system.dto.Role;
import com.greatwall.platform.system.service.ResourceService;
import com.greatwall.platform.system.service.RoleService;

@Controller
@RequestMapping("/system/role")
public class RoleController {

	@Autowired
	private RoleService roleService;
	
	@Autowired
	private ResourceService resourceService;
	
	@RequestMapping("/roleManage")
	public ModelAndView roleManage(Role role,ModelMap model){
		model.addAttribute("restree", resourceService.getResourcesTree(null));
		return new ModelAndView("/sysmanage/role/rolemanage.jsp");
	}
	
	@RequestMapping("/getRoles")
	public ModelAndView getRoles(Role role,PageParameter page,ModelMap model){

		model.addAttribute("roles", roleService.getRoles(role, page));
		model.addAttribute("page", page);
		return new ModelAndView("/sysmanage/role/roles.jsp");
	}
	@RequestMapping("/getRoleCount")
	public@ResponseBody Integer getRoleCount(Role role,PageParameter page){
		roleService.getRoles(role, page);
		return page.getTotalCount();
	}
	
	@RequestMapping("/getRoleTree")
	public@ResponseBody List<Map<String, Object>> getRoleTree(){
		List<Map<String, Object>> treeMap = new ArrayList<Map<String, Object>>();
		List<Role> roles = roleService.getRoles(null);
		if(roles!=null&&roles.size()>0){
			for(Role role:roles){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", role.getRoleId());
				map.put("pId", 0);
				map.put("name", role.getRoleName());
				map.put("open", true);
				treeMap.add(map);
			}
		}
		
		return treeMap;
	}
	
	@RequestMapping("/roleUserAssociation/{userId}")
	public ModelAndView roleUserAssociation(@PathVariable String userId,ModelMap model){
		model.addAttribute("roletree", JSONArray.fromObject(roleService.getRoleTree()));
		model.addAttribute("userId", userId);
		return new ModelAndView("/sysmanage/role/roleuser.jsp");
	}
	
}
