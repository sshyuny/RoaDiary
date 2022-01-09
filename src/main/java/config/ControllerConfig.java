package config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import account.AccountService;
import account.controller.AccountController;
import records.RecordsService;
import records.controller.RecordsController;

@Configuration
public class ControllerConfig {
    
    @Autowired
    private AccountService accountService;
    @Autowired
    private RecordsService recordsService;

    @Bean
    public AccountController accountThings() {
        AccountController controller = new AccountController();
        controller.setAccountService(accountService);
        return controller;
    }

    @Bean
    public RecordsController recordsController() {
        RecordsController controller = new RecordsController();
        controller.setRecordsController(recordsService);
        return controller;
    }
    
}
