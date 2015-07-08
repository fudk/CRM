package com.greatwall.platform.login.validate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.greatwall.platform.system.dto.User;



/**
 * 
 * @author fudk_k@sina.com
 * @update 2012-12-30
 */
public class LoginValidate implements Validator{

	private static final Pattern EMAIL_PATTERN  = 
			Pattern.compile("(?:\\w[-._\\w]*\\w@\\w[-._\\w]*\\w\\.\\w{2,3}$)");
	

	public boolean supports(Class<?> clazz) {  
		return clazz.equals(User.class);  
	}

	public void validate(Object arg0, Errors arg1) {
		User user = (User) arg0; 
		ValidationUtils.rejectIfEmptyOrWhitespace(arg1, "userName",
				"user.userName.required", "用户名必须填写");
		ValidationUtils.rejectIfEmptyOrWhitespace(arg1, "userPas",
				"user.userPas.required", "密码不能为空");
		validateEmail(user.getUserEmail(), arg1);
	}  

	private void validateEmail(String email, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userEmail",
				"required.email", "Email不能为空");
		Matcher m = EMAIL_PATTERN.matcher(email); 
		if (!m.matches()) {
			errors.rejectValue("userEmail", "invalid.email", "Email格式非法");
		}
	}
	
}
