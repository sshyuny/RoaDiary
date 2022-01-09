package controller;

// 회원가입 할 때, 사용하는 커맨드 객체
public class RegisterReqDto {
    
    private String email;
    private String password;
    private String confirmPassword;
    private String name;

    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isConfirmPasswordEqualto() {
        return password.equals(confirmPassword);
    }
}
