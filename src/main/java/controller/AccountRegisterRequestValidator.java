package controller;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import account.AccountRegisterRequest;

public class RegisterRequestValidator implements Validator{
    
    @Override
    public boolean supports(Class<?> clazz) {
        return AccountRegisterRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AccountRegisterRequest regReq = (AccountRegisterRequest)target;
        if(regReq.getEmail()==null || regReq.getEmail().trim().isEmpty()) {
            errors.rejectValue("email", "required");
        }
    }
}
