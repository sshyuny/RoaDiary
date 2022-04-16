package account.dto;

public class ChangeAccountReqDto {
    
    private String currentPassword;
    private String newPassword;
    private String newPasswordConfirm;

    public String getCurrentPassword() {
        return currentPassword;
    }
    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword()  {
        return newPassword;
    }
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPasswordConfirm()  {
        return newPasswordConfirm;
    }
    public void setNewPasswordConfirm(String newPasswordConfirm) {
        this.newPasswordConfirm = newPasswordConfirm;
    }

    public boolean isConfirmPasswordEqualto() {
        return newPassword.equals(newPasswordConfirm);
    }
}
