package com.greatwall.platform.system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.greatwall.platform.system.dto.Association;
import com.greatwall.platform.system.service.AssociationService;

@Controller
@RequestMapping("/system/association")
public class AssociationController {

	@Autowired
	private AssociationService associationService;
	
	@RequestMapping("/addOrDelAssociation")
	public@ResponseBody String addOrDelAssociation(Association association){
		associationService.addOrDelAssociation(association);
		return "success";
	}
	
	@RequestMapping("/getAssociations")
	public@ResponseBody List<Association> getAssociations(Association association){
		return associationService.getAssociations(association);
	}
	
	@RequestMapping("/addAssociation")
	public@ResponseBody String addAssociation(Association association){
		associationService.saveAssociation(association);
		return "success";
	}
	
	@RequestMapping("/delAssociation")
	public@ResponseBody String delAssociation(Association association){
		associationService.delAssociation(association);
		return "success";
	}
}
