package account;

// 로그인 정보 세션에 보관할 때 사용
public class LoginInfo {
    
    private Long id;
    private String email;
    private String name;

    public LoginInfo(Long id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }
    
    public String getName() {
        return name;
    }
}
