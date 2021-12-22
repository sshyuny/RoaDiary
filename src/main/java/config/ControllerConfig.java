package config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;

import account.AccountRegisterService;
import controller.AccountController;

@Configurable
public class ControllerConfig {
    
    @Autowired
    private AccountRegisterService accountRegisterService;

    @Bean
    public AccountController accountThings() {
        AccountController controller = new AccountController();
        controller.setAccountRegisterService(accountRegisterService);
        return controller;
    }
}
