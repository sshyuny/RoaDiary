package account;

import java.time.LocalDateTime;

import org.springframework.transaction.annotation.Transactional;

import account.dto.RegisterReqDto;
import account.exception.AccountNotFoundException;
import account.exception.DuplicateAccountException;
import account.exception.WrongIdPasswordException;
import domain.UserTb;

public class AccountService {
    
    private AccountDao accountDao;

    public AccountService(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    /**
     * 회원 가입
     * @param req
     * @return
     */
    public Long regist(RegisterReqDto req) {
        
        // 이미 등록된 email인지 확인
        UserTb userTb = accountDao.selectByEmail(req.getEmail());
        if(userTb != null) {
            throw new DuplicateAccountException("dup email" + req.getEmail());
        }
        // 새 UserTb 생성
        UserTb newUserTb = new UserTb(req.getEmail(), req.getName(), req.getPassword(), LocalDateTime.now(), LocalDateTime.now());

        // [DB]
        // 새 userTb 계정 DB에 insert
        accountDao.insert(newUserTb);
        return newUserTb.getId();
    }

    /**
     * LoginInfo 객체 생성(로그인 세션 생성 위한) 
     * @param email
     * @param password
     * @return
     */
    public LoginInfo authenticate(String email, String password) {
        // 요청된 이메일과 일치하는 userTb 생성
        UserTb userTb = accountDao.selectByEmail(email);
        // userTb 이상 확인
        if (userTb == null) {
            throw new WrongIdPasswordException();
        }
        if (!userTb.matchPassword(password)) {
            throw new WrongIdPasswordException();
        }
        // LoginInfo 객체 반환
        return new LoginInfo(
            userTb.getId(), 
            userTb.getEmail(), 
            userTb.getName()
        );
    }

    /**
     * 비밀번호 변경 
     * @param email
     * @param oldPwd
     * @param newPwd
     */
    @Transactional
    public void changeAccount(String email, String oldPwd, String newPwd) {
        /// 요청된 이메일과 일치하는 userTb 생성
        UserTb userTb = accountDao.selectByEmail(email);
        // userTb 이상 확인
        if(userTb == null)
            throw new AccountNotFoundException();
        // userTb 비밀번호 변경
        userTb.changePassword(oldPwd, newPwd);

        // [DB]
        // 비밀번호 변경된 userTb로 DB에 update
        accountDao.update(userTb);
    }

    public void changeAccountName(Long loginId, String newName) {
        // [DB] update
        accountDao.updateName(loginId, newName);
    }
}
