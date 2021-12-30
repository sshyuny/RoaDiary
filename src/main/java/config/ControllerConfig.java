package config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import account.AccountService;
import controller.AccountController;

@Configuration
public class ControllerConfig {
    
    @Autowired
    private AccountService accountService;

    @Bean
    public AccountController accountThings() {
        AccountController controller = new AccountController();
        controller.setAccountService(accountService);
        return controller;
    }
}
