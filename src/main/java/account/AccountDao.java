package account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import domain.UserDto;

public class AccountDao {

    private JdbcTemplate jdbcTemplate;

    public AccountDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private RowMapper<UserDto> accountRowMapper = 
        new RowMapper<UserDto>() {
            @Override
            public UserDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                UserDto account =  new UserDto(
                    rs.getString("email"),
                    rs.getString("name"),
                    rs.getString("password"),
                    rs.getTimestamp("regis_date").toLocalDateTime(),
                    rs.getTimestamp("lastvisit_date").toLocalDateTime()
                );
                account.setId(rs.getLong("user_id"));
                return account;
            }
        };

    public UserDto selectByEmail(String email) {
        List<UserDto> results = jdbcTemplate.query(
            "SELECT * FROM user WHERE email = ?", 
            accountRowMapper, email);

        return results.isEmpty() ? null : results.get(0);
    }

    public void updateLastVisitDate(LocalDateTime today, Long loginId) {
        jdbcTemplate.update(
            "UPDATE user SET lastvisit_date=? WHERE user_id = ?",
            today, loginId
        );
    }

    public void insert(final UserDto account) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator pre = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement pstmt = con.prepareStatement(
                    "INSERT INTO user(email, name, password, regis_date, lastvisit_date) " + 
                    "values (?, ?, ?, ?, ?)",
                    new String[] {"id"}
                );
                pstmt.setString(1, account.getEmail());
                pstmt.setString(2, account.getName());
                pstmt.setString(3, account.getPassword());
                pstmt.setTimestamp(4, Timestamp.valueOf(account.getRegisDate()));
                pstmt.setTimestamp(5, Timestamp.valueOf(account.getLastvisitDate()));
                return pstmt;
            }
        };
        jdbcTemplate.update(pre, keyHolder);
        Number keyValue = keyHolder.getKey();
        account.setId(keyValue.longValue());
    }

    public void update(UserDto account) {
        jdbcTemplate.update("UPDATE user SET name=?, password=? WHERE email=?", 
            account.getName(), account.getPassword(), account.getEmail());
    }

    public void updateName(Long loginId, String newName) {
        jdbcTemplate.update(
            "UPDATE user SET name=? WHERE user_id=?", 
            newName, loginId);
    }

    public void deleteUser(Long loginId) {
        jdbcTemplate.update(
            "UPDATE user SET password=null WHERE user_id = ?",
            loginId
        );
    }
}
