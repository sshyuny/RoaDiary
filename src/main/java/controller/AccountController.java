package controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
        model.addAttribute("AccountregisterRequest", new AccountRegisterRequest());
        return "account/register2";
    }

    @PostMapping("/account/registered")
    public String accountRegister(AccountRegisterRequest regReq) {
        try {
            accountService.regist(regReq);
            return "account/registered";
        } catch (DuplicateAccountException ex) {
            return "main";
        }
    }
    
}
