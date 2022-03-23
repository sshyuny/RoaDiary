package records.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalTime;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import domain.ThingsDto;

public class ThingsDao {
    
    private JdbcTemplate jdbcTemplate;

    public ThingsDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /*private RowMapper<ThingsDto> thingsRowMapper = 
        new RowMapper<ThingsDto>() {
            @Override
            public ThingsDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                ThingsDto thingsDto = new ThingsDto(
                    rs.getTimestamp("time").toLocalDateTime(),
                    rs.getString("content"),
                    rs.getLong("category_id")
                );
                thingsDto.setThingsId(rs.getLong("things_id"));
                thingsDto.setUserId(rs.getLong("user_id"));
                return thingsDto;
            }
        };*/

    public Long insert(final ThingsDto thingsDto) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator pre = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement pstmt = con.prepareStatement(
                    "INSERT INTO things(user_id, time, content, category_id) " + 
                    "VALUES (?, ?, ?, ?)",
                    new String[] {"things_id"}
                );
                pstmt.setLong(1, thingsDto.getUserId());
                pstmt.setTimestamp(2, Timestamp.valueOf(thingsDto.getTime()));
                pstmt.setString(3, thingsDto.getContent());
                pstmt.setLong(4, thingsDto.getCategoryId());
                return pstmt;
            }
        };
        jdbcTemplate.update(pre, keyHolder);
        Number keyValue = keyHolder.getKey();
        thingsDto.setThingsId(keyValue.longValue());
        // tag 테이블을 위해 키 return함
        Long keyValue2 = keyValue.longValue();
        return keyValue2;
    }

    // time 변경
    public void updateTime(LocalTime time, Long thingsId) {
        PreparedStatementCreator pre = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement pstmt = con.prepareStatement(
                    "UPDATE things " + 
                    "SET time = CONCAT(DATE(time), ' ', ?) " + 
                    "WHERE things_id = ?"
                );
                pstmt.setString(1, String.valueOf(time));
                pstmt.setLong(2, thingsId);
                return pstmt;
            }
        };
        jdbcTemplate.update(pre);
    }

    // content 변경
    public void updateContent(String content, Long thingsId) {
        jdbcTemplate.update(
            "UPDATE things " + 
            "SET content = ? " + 
            "WHERE things_id = ?",
            content, thingsId
        );
    }

    // things 하나의 행 삭제
    public void deleteOne(Long thingsId) {
        jdbcTemplate.update(
            "DELETE FROM things WHERE things_id = ?",
            thingsId
        );
    }

}
