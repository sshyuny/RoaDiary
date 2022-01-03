package account;

import java.time.LocalDateTime;

public class AccountService {
    
    private AccountDao accountDao;

    public AccountService(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    //===== 회원 가입 =====//
    public Long regist(AccountRegisterRequest req) {
        Account account = accountDao.selectByEmail(req.getEmail());
        if(account != null) {
            throw new DuplicateAccountException("dup email" + req.getEmail());
        }
        Account newAccount = new Account(req.getEmail(), req.getName(), req.getPassword(), LocalDateTime.now(), LocalDateTime.now());
        accountDao.insert(newAccount);
        return newAccount.getId();
    }
/*
    //===== LoginInfo 객체 생성(로그인 시 정보 확인) =====//
    public LoginInfo authenticate(String email, String password) {
        Account account = accountDao.selectByEmail(email);
        if (account == null) {
            throw new WrongIdPasswordException();
        }
        if ()
    }*/
}
