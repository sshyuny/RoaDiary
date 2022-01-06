package controller;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class ChangeAccountCommandValidator implements Validator{
    
    @Override
    public boolean supports(Class<?> clazz) {
        return ChangeAccountCommand.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "currentPassword", "required");
        ValidationUtils.rejectIfEmpty(errors, "newPassword", "required");
    }
}
