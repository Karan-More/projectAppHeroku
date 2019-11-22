package com.capstone.kanbantool.validator;

import com.capstone.kanbantool.domain.User;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
            User user = (User) o;

            if(user.getPassword().length() < 6){
                errors.rejectValue("password", "Length", "Minimum password length is 6 character");
            }

            if(!user.getPassword().equals(user.getConfirmPassword())){
                errors.rejectValue("confirmPassword","Match","Password and Confirm password must be same");
            }
    }
}
