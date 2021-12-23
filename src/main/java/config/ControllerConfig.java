package config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import account.AccountRegisterService;
import controller.AccountController;

@Configuration
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
