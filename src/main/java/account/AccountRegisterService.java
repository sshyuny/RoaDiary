package account;

import java.time.LocalDateTime;

public class AccountRegisterService {
    
    private AccountDao accountDao;

    public AccountRegisterService(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public Long regist(AccountRegisterRequest req) {
        Account account = accountDao.selectByEmail(req.getEmail());
        if(account != null) {
            throw new DuplicateAccountException("dup email" + req.getEmail());
        }
        Account newAccount = new Account(req.getEmail(), req.getPassword(), req.getName(), LocalDateTime.now(), LocalDateTime.now());
        accountDao.insert(newAccount);
        return newAccount.getId();
    }
}
