package com.vitorgsevero.kanbantool.KanbanTool2.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.vitorgsevero.kanbantool.KanbanTool2.domain.User;

@Component
public class UserValidator implements Validator{

	@Override
	public boolean supports(Class<?> aClass) {
		return User.class.equals(aClass);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		User user = (User) obj;
		
		if(user.getPassword().length() < 6) {
			errors.rejectValue("password", "Length", "Password must be at least 6 characters"); 
		}
		
		if(!user.getPassword().equalsIgnoreCase(user.getConfirmPassword())) {
			errors.rejectValue("confirmPassword", "Match", "Password must match"); 
		}
	}

}
