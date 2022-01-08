package config;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import account.AccountDao;
import account.AccountService;
import account.LoginInfo;
import records.RecordsDao;
import records.RecordsService;

@Configuration
public class AccountConfig {
    
    @Bean(destroyMethod="close")
    public DataSource dataSource() {
        DataSource ds = new DataSource();
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        ds.setUrl("jdbc:mysql://localhost/rddb?characterEncoding=utf8&useSSL=false");  // SSL 설정 변경
        ds.setUsername("rduser");
        ds.setPassword("rduserpw");
        ds.setInitialSize(5);
        ds.setMaxActive(10);
        return ds;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        DataSourceTransactionManager tm= new DataSourceTransactionManager();
        tm.setDataSource(dataSource());
        return tm;
    }

    //==== account
    @Bean
    public AccountDao accountDao() {
        return new AccountDao(dataSource());
    }

    @Bean
    public AccountService accountService() {
        return new AccountService(accountDao());
    }

    //===== Records
    @Bean
    public RecordsDao recordsDao() {
        return new RecordsDao(dataSource());
    }

    @Bean
    public RecordsService recordsService() {
        return new RecordsService(recordsDao());
    }

}
