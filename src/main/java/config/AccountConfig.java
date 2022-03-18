package config;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import account.AccountDao;
import account.AccountService;
import records.RecordsService;
import records.dao.JoinDao;
import records.dao.TagDao;
import records.dao.ThingsDao;
import records.dao.ThingsTagDao;

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
        // 아래는 DataAccessResourceFailureException를 해결하기 위한 설정
        ds.setValidationQuery("SELECT 1");
        ds.setTestOnBorrow(false);
        ds.setTestOnReturn(false);
        ds.setTestWhileIdle(true);
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
    public ThingsDao thingsDao() {
        return new ThingsDao(dataSource());
    }

    @Bean
    public TagDao tagDao() {
        return new TagDao((dataSource()));
    }

    @Bean
    public ThingsTagDao thingsTagDao() {
        return new ThingsTagDao(dataSource());
    }

    @Bean
    public JoinDao joinDao() {
        return new JoinDao(dataSource());
    }

    @Bean
    public RecordsService recordsService() {
        return new RecordsService(thingsDao(), tagDao(), thingsTagDao(), joinDao());
    }

}
