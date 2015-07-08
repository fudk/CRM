package com.greatwall.platform.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.greatwall.platform.system.dto.Resource;
import com.greatwall.platform.system.service.ResourceService;

@Controller
@RequestMapping("/system/resource")
public class ResourceController {

	@Autowired 
	@Qualifier("defaultResourceService")
	private ResourceService resourceService;
	
	@RequestMapping("/getResources")
	public ModelAndView getResources(ModelMap model){
		model.addAttribute("restree", resourceService.getResourcesTree(null));
		return new ModelAndView("/sysmanage/resource/resource.jsp");
	}
	
	@RequestMapping("/getRoleResources/{roleId}")
	public ModelAndView getRoleResources(@PathVariable String roleId,ModelMap model){
		
		model.addAttribute("roleId", roleId);
		model.addAttribute("restree", resourceService.getResourcesTree(null));
		return new ModelAndView("/sysmanage/resource/resourcerole.jsp");
	}
	
	@RequestMapping("/updateResource")
	public@ResponseBody String updateResource(Resource resource){
		resourceService.updateResource(resource);
		return "success";
	}
	
	@RequestMapping("/updateResourceParent")
	public@ResponseBody String updateResourceParent(Resource resource){
		resourceService.updateResourceParent(resource);
		return "success";
	}
	
	/*@RequestMapping("/getResource")
	public@ResponseBody  Resource getResource(@RequestParam(value="resId") int resId){
		return resourceService.getResource(resId);
	}*/
	
	@RequestMapping("/getResource/{resId}")
	public ModelAndView getResource(@PathVariable int resId,ModelMap model){
		model.addAttribute("resource", resourceService.getResource(resId));
		return new ModelAndView("/sysmanage/resource/updateResource.jsp");
	}
	
	@RequestMapping("/delResource")
	public@ResponseBody String delResource(@RequestParam(value="resId") int resId){
		resourceService.delResource(resId);
		return "success";
	}
	
	@RequestMapping("/addResource")
	public@ResponseBody String addResource(
			@RequestParam(value="pId") int pId,
			@RequestParam(value="level") int level,
			@RequestParam(value="resName") String resName){
		Resource resource = new Resource();
		resource.setParentId(pId);
		resource.setResName(resName);
		resource.setLevel(level);
		
		Resource res = resourceService.saveResource(resource);
		
		return res.getResId()+"";
	}
	
}
