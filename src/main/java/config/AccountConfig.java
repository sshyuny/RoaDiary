package config;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import account.AccountDao;

@Configuration
public class AccountConfig {
    
    @Bean(destroyMethod="close")
    public DataSource dataSource() {
        DataSource ds = new DataSource();
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        ds.setUrl("jdbc:mysql://localhost/spring5fs?characterEncoding=utf8&useSSL=false");  // SSL 설정 변경
        ds.setUsername("rduser");
        ds.setPassword("rduserpw");
        ds.setInitialSize(5);
        ds.setMaxActive(10);
        return ds;
    }

    @Bean
    public AccountDao accountDao() {
        return new AccountDao(dataSource());
    }

}
