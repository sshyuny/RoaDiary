package account;

import java.time.LocalDateTime;

import org.springframework.transaction.annotation.Transactional;

import controller.RegisterReqDto;

public class AccountService {
    
    private AccountDao accountDao;

    public AccountService(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    //===== 회원 가입 =====//
    public Long regist(RegisterReqDto req) {
        Account account = accountDao.selectByEmail(req.getEmail());
        if(account != null) {
            throw new DuplicateAccountException("dup email" + req.getEmail());
        }
        Account newAccount = new Account(req.getEmail(), req.getName(), req.getPassword(), LocalDateTime.now(), LocalDateTime.now());
        accountDao.insert(newAccount);
        return newAccount.getId();
    }

    //===== LoginInfo 객체 생성(로그인 시 정보 확인 로그인 세션 생성 위한) =====//
    public LoginInfo authenticate(String email, String password) {
        Account account = accountDao.selectByEmail(email);
        if (account == null) {
            throw new WrongIdPasswordException();
        }
        if (!account.matchPassword(password)) {
            throw new WrongIdPasswordException();
        }
        return new LoginInfo(
            account.getId(), 
            account.getEmail(), 
            account.getName()
            );
    }

    //===== 비밀번호 변경 =====//
    @Transactional
    public void changeAccount(String email, String oldPwd, String newPwd) {
        Account account = accountDao.selectByEmail(email);
        if(account == null)
            throw new AccountNotFoundException();

        account.changePassword(oldPwd, newPwd);

        accountDao.update(account);
    }
}
