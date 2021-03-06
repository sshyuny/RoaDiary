package account.controller;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import account.AccountService;
import account.dto.ChangeAccountReqDto;
import account.dto.LoginInfo;
import account.dto.LoginReqDto;
import account.dto.RegisterReqDto;
import account.exception.DuplicateAccountException;
import account.exception.WithdrawalAccountException;
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

    //===== ===== ===== =====
    // 홈페이지
    //===== ===== ===== =====
    @RequestMapping("/")
    public String main() {
        return "main";
    }
    /**
     * 이전 url로 접근했을 경우 안내 페이지로 연결
     * @return
     */
    @GetMapping("/RoaDiary")
    public String beforeMain() {
        return "beforeMain";
    }

    //===== ===== ===== =====
    // 계정 생성
    //===== ===== ===== =====
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
     * @param request  약관동의 확인
     * @param model  dto
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
     * @param registerReqDto  dto
     * @param errors  validator
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

    //===== ===== ===== =====
    // 로그인하기
    //===== ===== ===== =====
    @GetMapping("/account/login")
    public String loginForm(LoginReqDto loginReqDto) {
        return "account/loginForm";
    }
    /**
     * 
     * @param loginReqDto  dto
     * @param errors  validator
     * @param session  세션 생성
     * @return
     */
    @PostMapping("/account/login")
    public String loginSubmit(LoginReqDto loginReqDto, Errors errors, HttpSession session, HttpServletRequest request) {
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

            // 페이지에서 보내는 정보 가져오기
            String redirectURL = request.getParameter("redirectUrl");
            String redirectUrlTag = request.getParameter("redirectUrlTag");

            // 페이지에서 보내는 정보가 있을 경우(일반 경로 로그인 페이지가 아닐 경우)
            if (redirectURL != "") {
                try {
                    String tagEncoded = URLEncoder.encode(redirectUrlTag, "UTF-8");
                    String urlEncoded = redirectURL + tagEncoded;
                    return "redirect:/"+ urlEncoded;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } 
            
            // 일반 경로 로그인 페이지일 경우, 로그인 성공 페이지로
            return "account/loginSuccess";
        } catch(WrongIdPasswordException e) {
            // 이메일과 비밀번호 일치 안되면, 예외처리
            errors.reject("idPasswordNotMatching");
            return "account/loginForm";
        } catch(WithdrawalAccountException e) {
            // 탈퇴한 계정이면, 예외처리
            return "account/withdrawalAccount";
        }
    }
    /**
     * 방문자용 로그인(바로 로그인하여 세션이 생성됩니다.)
     * @param session
     * @return
     */
    @GetMapping("/loginForVisitor")
    public String loginSubmitForVisitor(HttpSession session) {

        // 세션 생성
        // LoginInfo loginInfo = new LoginInfo();
        LoginInfo loginInfo = accountService.authenticate(
            "visitor@visitor.com", 
            "visitorpw"
        );
        session.setAttribute("loginInfo", loginInfo);

        // 메인 페이지로 바로 이동합니다.
        return "main";
    }

    //===== ===== ===== =====
    // 로그아웃하기
    //===== ===== ===== =====
    @RequestMapping("/account/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    //===== ===== ===== =====
    // 계정 정보 변경하기
    //===== ===== ===== =====
    /**
     * 계정 정보 변경 페이지로 이동합니다.
     * @param session
     * @param model
     * @param changeReqDto
     * @return
     */
    @GetMapping("/account/change")
    public String changeForm(HttpSession session, Model model, 
            @ModelAttribute("command") ChangeAccountReqDto changeReqDto) {

        LoginInfo loginInfo = (LoginInfo) session.getAttribute("loginInfo");
        String loginEmail = loginInfo.getEmail();
        model.addAttribute("loginEmail", loginEmail);
        String currentName = loginInfo.getName();
        model.addAttribute("currentName", currentName);

        // 방문자 계정(visitor)일 경우, 계정 정보 변경을 하지 못하도록 다른 페이지로 연결합니다.
        if (loginEmail.equals("visitor@visitor.com")) return "account/cannotChangeAccount";

        return "account/changeForm";
    }
    /**
     * 비밀번호 변경을 진행합니다.
     * 이는 계정 정보 변경 페이지(changeForm)에서 진행됩니다.
     * @param changeReqDto  dto
     * @param errors  validator
     * @param session  이미 등록된 세션 가져오기 위한
     * @return
     */
    @PostMapping("/account/change")
    public String changeSubmit(
            @ModelAttribute("command") ChangeAccountReqDto changeReqDto,
            Errors errors, 
            HttpSession session) {

        // 이미 등록된 세션으로 LoginInfo 객체 생성
        LoginInfo loginInfo = (LoginInfo) session.getAttribute("loginInfo");

        // validator 설정
        new ChangeAccountReqDtoValidator().validate(changeReqDto, errors);
        if(errors.hasErrors()) {
            return "account/changeForm";
        }

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
    /**
     * 계정의 이름(name)을 변경합니다.
     * 이는 계정 정보 변경 페이지(changeForm)에서 진행됩니다.
     * @param session
     * @param model
     * @param httpServletRequest
     * @return
     */
    @PostMapping("/account/changeName")
    public String changeNamePost(HttpSession session, Model model, 
            HttpServletRequest httpServletRequest) {

        LoginInfo loginInfo = (LoginInfo) session.getAttribute("loginInfo");
        Long loginId = loginInfo.getId();

        // 변경하고자 하는 name request됨
        String newName = httpServletRequest.getParameter("newName");
        accountService.changeAccountName(loginId, newName);

        // login 세션 name변경함(안하면, 로그아웃 전까지 이전 name으로 세션에 저장돼있음)
        loginInfo.setName(newName);

        String currentName = loginInfo.getName();
        model.addAttribute("currentName", currentName);

        return "account/changeForm";
    }

    @GetMapping("/account/withdrawal")
    public String withdrawalGet(HttpSession session) {
        LoginInfo loginInfo = (LoginInfo) session.getAttribute("loginInfo");
        String loginEmail = loginInfo.getEmail();

        // 방문자 계정(visitor)일 경우, 계정 정보 변경을 하지 못하도록 다른 페이지로 연결합니다.
        if (loginEmail.equals("visitor@visitor.com")) return "account/cannotChangeAccount";

        return "account/withdrawal";
    }
    @PostMapping("/account/withdrawal")
    public String withdrawalPost(HttpSession session) {
        
        LoginInfo loginInfo = (LoginInfo) session.getAttribute("loginInfo");
        Long loginId = loginInfo.getId();
        
        // 탈퇴하기
        accountService.deleteAccount(loginId);

        // 탈퇴 처리 후 세션 제거
        session.invalidate();

        return "account/withdrawalCompleted";
    }


}
