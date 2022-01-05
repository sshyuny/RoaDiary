package controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import account.AccountRegisterRequest;
import account.AccountService;
import account.DuplicateAccountException;

@Controller
public class AccountController {

    private AccountService accountService;

    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    // 테스트 
    @RequestMapping("/")
    public String main() {
        return "main";
    }

    @GetMapping("/account/register1")
    public String register1() {
        return "account/register1";
    }

    @PostMapping("/account/register2")
    public String register2(HttpServletRequest request, Model model) {
        String agreeParam = request.getParameter("agree");
        if(agreeParam == null || !agreeParam.equals("true")) {
            return "account/register1";
        }
        model.addAttribute("accountRegisterRequest", new AccountRegisterRequest());
        return "account/register2";
    }

    @PostMapping("/account/registered")
    public String accountRegister(AccountRegisterRequest accountRegisterRequest, Errors errors, Model model) {
        AccountRegisterRequestValidator newAccRegReqVal = new AccountRegisterRequestValidator();
        newAccRegReqVal.validate(accountRegisterRequest, errors);
        model.addAttribute("AccountRegisterRequest", newAccRegReqVal);
        //new AccountRegisterRequestValidator().validate(accountRegisterRequest, errors);
        //model.addAttribute("AccountRegisterRequest", new AccountRegisterRequest());
        //@ModelAttribute("accountRegisterRequest") @Valid AccountRegisterRequest accountRegisterRequest
        if(errors.hasErrors()) {
            return "account/register2";
        }
        try {
            accountService.regist(accountRegisterRequest);
            return "account/registered";
        } catch (DuplicateAccountException ex) {
            errors.rejectValue("email", "duplicate");
            return "account/register2";
        }
    }
    
}
