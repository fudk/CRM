package com.greatwall.platform.system.validate;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class RechargeValidate implements Validator{

	public boolean supports(Class<?> arg0) {
		return false;
	}

	public void validate(Object arg0, Errors arg1) {
		
	}

}
