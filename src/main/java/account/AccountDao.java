package account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
//import java.time.LocalDateTime;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public class AccountDao {

    private JdbcTemplate jdbcTemplate;

    public AccountDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private RowMapper<Account> accountRowMapper = 
        new RowMapper<Account>() {
            @Override
            public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
                Account account =  new Account(
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("name"),
                    rs.getTimestamp("regis_date").toLocalDateTime(),
                    rs.getTimestamp("lastvisit_date").toLocalDateTime()
                );
                account.setId(rs.getLong("id"));
                return account;
            }
        };

    public Account selectByEmail(String email) {
        List<Account> results = jdbcTemplate.query(
            "SELECT * FROM users WHERE email = ?", 
            accountRowMapper, email);

        return results.isEmpty() ? null : results.get(0);
    }

    public void insert(final Account account) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator pre = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement pstmt = con.prepareStatement(
                    "INSERT INTO users(email, password, name, regis_date, lastvisit_date " + 
                    "values (?, ?, ?, ?, ?)",
                    new String[] {"id"}
                );
                pstmt.setString(1, account.getEmail());
                pstmt.setString(2, account.getPassword());
                pstmt.setString(3, account.getName());
                pstmt.setTimestamp(4, Timestamp.valueOf(account.getRegis_date()));
                pstmt.setTimestamp(5, Timestamp.valueOf(account.getLastvisit_date()));
                return pstmt;
            }
        };
        jdbcTemplate.update(pre, keyHolder);
        Number keyValue = keyHolder.getKey();
        account.setId(keyValue.longValue());
    }
}
