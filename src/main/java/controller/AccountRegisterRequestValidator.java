package controller;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import account.AccountRegisterRequest;

public class AccountRegisterRequestValidator implements Validator{
    
    @Override
    public boolean supports(Class<?> clazz) {
        return AccountRegisterRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AccountRegisterRequest regReq = (AccountRegisterRequest)target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "required");
        ValidationUtils.rejectIfEmpty(errors, "password", "required");
        ValidationUtils.rejectIfEmpty(errors, "confirmPassword", "required");
        if(!regReq.getPassword().isEmpty()) {
            if(!regReq.isConfirmPasswordEqualto()) {
                errors.rejectValue("confirmPassword", "nonmatch");
            }
        }
    }
}
