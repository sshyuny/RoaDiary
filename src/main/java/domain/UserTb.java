package domain;

import java.time.LocalDateTime;

import account.exception.WrongIdPasswordException;

public class UserTb {
    private Long id;
    private String email;
    private String password;
    private String name;
    private LocalDateTime regisDate;
    private LocalDateTime lastvisitDate;

    public UserTb(String email, String name, String password, LocalDateTime regisDate, LocalDateTime lastvisitDate) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.regisDate = regisDate;
        this.lastvisitDate = lastvisitDate;
    }

    //===== 게터와 세터 설정 =====//
    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() {
        return email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getPassword() {
        return password;
    }

    public void setRegisDate(LocalDateTime regisDate) {
        this.regisDate = regisDate;
    }
    public LocalDateTime getRegisDate() {
        return regisDate;
    }

    public void setLastvisitDate(LocalDateTime lastvisitDate) {
        this.lastvisitDate = lastvisitDate;
    }
    public LocalDateTime getLastvisitDate() {
        return lastvisitDate;
    }

    //===== 로그인할 때 암호 일치 여부 확인 =====//
    public boolean matchPassword(String password) {
        return this.password.equals(password);
    }

    //===== 비밀번호 변경 =====//
    public void changePassword(String oldPwd, String newPwd) {
        if(!password.equals(oldPwd)){
            throw new WrongIdPasswordException();
        }
        this.password = newPwd;
    }

}
