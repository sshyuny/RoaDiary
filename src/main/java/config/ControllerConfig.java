package config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import account.AccountService;
import controller.AccountController;
//import controller.LoginController;

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

    /*@Bean
    public LoginController loginController() {
        LoginController controller = new LoginController();
        controller.setAccountService(accountService);
        return controller;
    }*/
}
