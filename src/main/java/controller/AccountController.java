package controller;

import javax.servlet.http.HttpServletRequest;
//import javax.validation.Valid;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import account.AccountService;
import account.DuplicateAccountException;
import account.LoginInfo;
import account.WrongIdPasswordException;

@Controller
public class AccountController {

    private AccountService accountService;

    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    //===== 홈페이지 =====//
    @RequestMapping("/")
    public String main() {
        return "main";
    }

    //===== 계정 생성 =====//
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
        model.addAttribute("registerReqDto", new RegisterReqDto());
        return "account/register2";
    }

    @PostMapping("/account/registered")
    public String accountRegister(RegisterReqDto registerReqDto, Errors errors) {
        //AccountRegisterRequestValidator newAccRegReqVal = new AccountRegisterRequestValidator();
        //newAccRegReqVal.validate(accountRegisterRequest, errors);
        //model.addAttribute("AccountRegisterRequest", newAccRegReqVal);
        new RegisterReqDtoValidator().validate(registerReqDto, errors);

        if(errors.hasErrors()) {
            return "account/register2";
        }
        try {
            accountService.regist(registerReqDto);
            return "account/registered";
        } catch (DuplicateAccountException ex) {
            errors.rejectValue("email", "duplicate");
            return "account/register2";
        }
    }

    //===== 로그인하기 =====//
    @GetMapping("/account/login")
    public String loginForm(LoginReqDto loginReqDto) {
        return "account/loginForm";
    }

    @PostMapping("/account/login")
    public String loginSubmit(LoginReqDto loginReqDto, Errors errors, HttpSession session) {
        new LoginReqDtoValidator().validate(loginReqDto, errors);
        if(errors.hasErrors()) {
            return "account/loginForm";
        }
        try{
            LoginInfo loginInfo = accountService.authenticate(
                loginReqDto.getEmail(), 
                loginReqDto.getPassword()
                );

            session.setAttribute("loginInfo", loginInfo);

            return "account/loginSuccess";
        } catch(WrongIdPasswordException e) {
            errors.reject("idPasswordNotMatching");
            return "account/loginForm";
        }
    }

    //===== 로그아웃하기 =====//
    @RequestMapping("/account/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    //===== 비밀번호 변경하기 =====//
    @GetMapping("/account/change")
    public String changeForm(
        @ModelAttribute("command") ChangeAccountReqDto changeReqDto) {
        return "account/changeForm";
    }
    @PostMapping("/account/change")
    public String changeSubmit(
            @ModelAttribute("command") ChangeAccountReqDto changeReqDto,
            Errors errors,
            HttpSession session) {
        new ChangeAccountReqDtoValidator().validate(changeReqDto, errors);

        if(errors.hasErrors()) {
            return "account/changeForm";
        }
        LoginInfo loginInfo = (LoginInfo) session.getAttribute("loginInfo");
        try {
            accountService.changeAccount(
                    loginInfo.getEmail(),
                    changeReqDto.getCurrentPassword(),
                    changeReqDto.getNewPassword()
                );
            return "account/changeCompleted";
        } catch(WrongIdPasswordException e) {
            errors.rejectValue("currentPassword", "notMatching");
            return "account/changeForm";
        }
    }

}
