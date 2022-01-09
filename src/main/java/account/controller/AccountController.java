package account.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import account.AccountService;
import account.LoginInfo;
import account.dto.ChangeAccountReqDto;
import account.dto.LoginReqDto;
import account.dto.RegisterReqDto;
import account.exception.DuplicateAccountException;
import account.exception.WrongIdPasswordException;
import account.validator.ChangeAccountReqDtoValidator;
import account.validator.LoginReqDtoValidator;
import account.validator.RegisterReqDtoValidator;

@Controller
public class AccountController {

    private AccountService accountService;

    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    //===== ===== ===== =====//
    // 홈페이지
    //===== ===== ===== =====//
    @RequestMapping("/")
    public String main() {
        return "main";
    }

    //===== ===== ===== =====//
    // 계정 생성
    //===== ===== ===== =====//
    /**
     * 계정생성 1단계: 약관 동의 페이지 연결
     * @return
     */
    @GetMapping("/account/register1")
    public String register1() {
        return "account/register1";
    }
    /**
     * 계정생성 2단계
     * @param request // 약관동의 확인
     * @param model   // dto
     * @return
     */
    @PostMapping("/account/register2")
    public String register2(HttpServletRequest request, Model model) {
        // 1단 계페이지에서 약관 동의 체크 유무 확인
        String agreeParam = request.getParameter("agree");
        if(agreeParam == null || !agreeParam.equals("true")) {
            return "account/register1";
        }
        // 동의했으면, 회원 가입 창으로 연결
        model.addAttribute("registerReqDto", new RegisterReqDto());
        return "account/register2";
    }
    /**
     * 계정생성 3단계
     * @param registerReqDto // dto
     * @param errors         // validator
     * @return
     */
    @PostMapping("/account/registered")
    public String accountRegister(RegisterReqDto registerReqDto, Errors errors) {
        new RegisterReqDtoValidator().validate(registerReqDto, errors);

        if(errors.hasErrors()) {
            return "account/register2";
        }
        try {
            // 회원가입
            accountService.regist(registerReqDto);
            return "account/registered";
        } catch (DuplicateAccountException ex) {
            // 이미 등록된 이메일이면, 예외처리
            errors.rejectValue("email", "duplicate");
            return "account/register2";
        }
    }

    //===== ===== ===== =====//
    // 로그인하기
    //===== ===== ===== =====//
    @GetMapping("/account/login")
    public String loginForm(LoginReqDto loginReqDto) {
        return "account/loginForm";
    }
    /**
     * 
     * @param loginReqDto  // dto
     * @param errors       // validator
     * @param session      // 세션 생성
     * @return
     */
    @PostMapping("/account/login")
    public String loginSubmit(LoginReqDto loginReqDto, Errors errors, HttpSession session) {
        // validator 설정
        new LoginReqDtoValidator().validate(loginReqDto, errors);
        if(errors.hasErrors()) {
            return "account/loginForm";
        }
        // 로그인 시도
        try{
            // 세션 생성
            LoginInfo loginInfo = accountService.authenticate(
                loginReqDto.getEmail(), 
                loginReqDto.getPassword()
                );
            session.setAttribute("loginInfo", loginInfo);
            // 로그인 성공 페이지로
            return "account/loginSuccess";
        } catch(WrongIdPasswordException e) {
            // 이메일과 비밀번호 일치 안되면, 예외처리
            errors.reject("idPasswordNotMatching");
            return "account/loginForm";
        }
    }

    //===== ===== ===== =====//
    // 로그아웃하기
    //===== ===== ===== =====//
    @RequestMapping("/account/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    //===== ===== ===== =====//
    // 비밀번호 변경하기
    //===== ===== ===== =====//
    @GetMapping("/account/change")
    public String changeForm(
        @ModelAttribute("command") ChangeAccountReqDto changeReqDto) {
        return "account/changeForm";
    }
    /**
     * 
     * @param changeReqDto  // dto
     * @param errors        // validator
     * @param session       // 이미 등록된 세션 가져오기 위한
     * @return
     */
    @PostMapping("/account/change")
    public String changeSubmit(
            @ModelAttribute("command") ChangeAccountReqDto changeReqDto,
            Errors errors,
            HttpSession session) {
        // validator 설정
        new ChangeAccountReqDtoValidator().validate(changeReqDto, errors);
        if(errors.hasErrors()) {
            return "account/changeForm";
        }
        // 이미 등록된 세션으로 LoginInfo 객체 생성
        LoginInfo loginInfo = (LoginInfo) session.getAttribute("loginInfo");
        // 비밀번호 변경 시도
        try {
            accountService.changeAccount(
                    loginInfo.getEmail(),               // 세션에서 가져옴
                    changeReqDto.getCurrentPassword(),  // dto에서 가져옴
                    changeReqDto.getNewPassword()       // dto에서 가져옴
                );
            // 비밀번호 변경 완료 페이지로
            return "account/changeCompleted";
        } catch(WrongIdPasswordException e) {
            //  이메일과 비밀번호 일치 안되면, 예외처리
            errors.rejectValue("currentPassword", "notMatching");
            return "account/changeForm";
        }
    }

}
