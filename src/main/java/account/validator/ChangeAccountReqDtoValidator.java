package account.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import account.dto.ChangeAccountReqDto;

public class ChangeAccountReqDtoValidator implements Validator{
    
    @Override
    public boolean supports(Class<?> clazz) {
        return ChangeAccountReqDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ChangeAccountReqDto regReq = (ChangeAccountReqDto)target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "currentPassword", "required");
        ValidationUtils.rejectIfEmpty(errors, "newPassword", "required");
        if(!regReq.getNewPassword().isEmpty()) {
            if(!regReq.isConfirmPasswordEqualto()) {
                errors.rejectValue("newPasswordConfirm", "nonmatch");
            }
        }
    }
}
