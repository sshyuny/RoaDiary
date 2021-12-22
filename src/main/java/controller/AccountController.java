package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import account.AccountRegisterRequest;
import account.AccountRegisterService;
import account.DuplicateAccountException;

@Controller
public class AccountController {

    private AccountRegisterService accountRegisterService;

    public void setAccountRegisterService(AccountRegisterService accountRegisterService) {
        this.accountRegisterService = accountRegisterService;
    }

    // 테스트 
    @RequestMapping("/")
    public String main() {
        return "main";
    }

    @PostMapping("/account/register")
    public String accountRegister(AccountRegisterRequest regReq) {
        try {
            accountRegisterService.regist(regReq);
            return "account/register";
        } catch (DuplicateAccountException ex) {
            return "main";
        }
    }
    
}
