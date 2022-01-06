package account;

// 회원가입 할 때, 이미 등록된 이메일 주소로 가입하려할 때 나타나는 exception
public class DuplicateAccountException extends RuntimeException {
    public DuplicateAccountException(String message) {
        super(message);
    }
}
