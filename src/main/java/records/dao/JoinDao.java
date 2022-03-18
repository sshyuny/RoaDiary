package records.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import domain.JoinThingsTagDto;

public class JoinDao {
    
    private JdbcTemplate jdbcTemplate;

    public JoinDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private RowMapper<JoinThingsTagDto> joinRowMapper = 
        new RowMapper<JoinThingsTagDto>() {
            @Override
            public JoinThingsTagDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                JoinThingsTagDto joinTb = new JoinThingsTagDto(
                    rs.getLong("things_id"),
                    rs.getLong("user_id"),
                    rs.getTimestamp("time").toLocalDateTime(),
                    rs.getString("content"),
                    rs.getLong("category_id"),
                    rs.getInt("tag_id"),
                    rs.getString("name")
                );
                return joinTb;
            }
        };

    public List<JoinThingsTagDto> selectByDate(LocalDate date, Long userId) {
        List<JoinThingsTagDto> results = jdbcTemplate.query(
            "SELECT things.things_id, user_id, time, content, category_id, tag.tag_id, name FROM things " + 
            "LEFT JOIN things_tag ON things.things_id = things_tag.things_id " + 
            "LEFT JOIN tag ON things_tag.tag_id = tag.tag_id " + 
            "WHERE user_id=? AND DATE(time)=? " + 
            "ORDER BY time ASC, things_id ASC", // 태그 여러개 붙이는 기능 때문에(RecordsService) things_id 정렬 필요합니다.
            joinRowMapper,
            userId, date
        );
        return results;
    }

    public List<JoinThingsTagDto> selectByDatePeriod(LocalDate dateFrom, LocalDate dateTo, Long userId) {
        List<JoinThingsTagDto> results = jdbcTemplate.query(
            "SELECT things.things_id, user_id, time, content, category_id, tag.tag_id, name FROM things " + 
            "LEFT JOIN things_tag ON things.things_id = things_tag.things_id " + 
            "LEFT JOIN tag ON things_tag.tag_id = tag.tag_id " + 
            "WHERE user_id=? AND " + 
            "time BETWEEN ? AND ? " +
            "ORDER BY time ASC, things_id ASC", // 태그 여러개 붙이는 기능 때문에(RecordsService) things_id 정렬 필요합니다.
            joinRowMapper,
            userId, dateFrom, dateTo
        );
        return results;
    }
}
