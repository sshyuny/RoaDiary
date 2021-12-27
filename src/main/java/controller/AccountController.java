package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/account/register1")
    public String register1() {
        return "account/register1";
    }

    @PostMapping("/account/register2")
    public String register2() {
        return "account/register2";
    }

    @PostMapping("/account/registered")
    public String accountRegister(AccountRegisterRequest regReq) {
        try {
            accountRegisterService.regist(regReq);
            return "account/registered";
        } catch (DuplicateAccountException ex) {
            return "main";
        }
    }
    
}
