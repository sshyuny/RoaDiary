package account.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import account.dto.LoginReqDto;

public class LoginReqDtoValidator implements Validator{
    
    @Override
    public boolean supports(Class<?> clazz) {
        return LoginReqDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "required");
        ValidationUtils.rejectIfEmpty(errors, "password", "required");
    }
}
