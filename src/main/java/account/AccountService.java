package account;

import java.time.LocalDateTime;

import org.springframework.transaction.annotation.Transactional;

import account.dto.LoginInfo;
import account.dto.RegisterReqDto;
import account.exception.AccountNotFoundException;
import account.exception.DuplicateAccountException;
import account.exception.WithdrawalAccountException;
import account.exception.WrongIdPasswordException;
import domain.UserDto;

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
        UserDto userDto = accountDao.selectByEmail(req.getEmail());
        if(userDto != null) {
            throw new DuplicateAccountException("dup email" + req.getEmail());
        }
        // 새 UserDto 생성
        UserDto newUserDto = new UserDto(req.getEmail(), req.getName(), req.getPassword(), LocalDateTime.now(), LocalDateTime.now());

        // [DB]
        // 새 userDto 계정 DB에 insert
        accountDao.insert(newUserDto);
        return newUserDto.getId();
    }

    /**
     * LoginInfo 객체 생성(로그인 세션 생성 위한) 
     * @param email
     * @param password
     * @return
     */
    public LoginInfo authenticate(String email, String password) {
        // 요청된 이메일과 일치하는 userDto 생성
        UserDto userDto = accountDao.selectByEmail(email);
        // userDto 이상 확인
        if (userDto == null) {
            throw new WrongIdPasswordException();
        }
        if (userDto.getPassword() == null) {
            // 탈퇴한 계정일 경우 exception
            throw new WithdrawalAccountException();
        }
        if (!userDto.matchPassword(password)) {
            throw new WrongIdPasswordException();
        }

        // user 테이블의 regis_date 오늘 날짜로 업데이트
        accountDao.updateLastVisitDate(LocalDateTime.now(), userDto.getId());
        
        // LoginInfo 객체 반환
        return new LoginInfo(
            userDto.getId(), 
            userDto.getEmail(), 
            userDto.getName()
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
        /// 요청된 이메일과 일치하는 userDto 생성
        UserDto userDto = accountDao.selectByEmail(email);
        // userDto 이상 확인
        if(userDto == null)
            throw new AccountNotFoundException();
        // userDto 비밀번호 변경
        userDto.changePassword(oldPwd, newPwd);

        // [DB]
        // 비밀번호 변경된 userDto로 DB에 update
        accountDao.update(userDto);
    }

    public void changeAccountName(Long loginId, String newName) {
        // [DB] update
        accountDao.updateName(loginId, newName);
    }

    public void deleteAccount(Long loginId) {
        // [DB] delete
        accountDao.deleteUser(loginId);
    }
}
