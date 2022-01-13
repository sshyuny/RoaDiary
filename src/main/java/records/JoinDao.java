package records;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import domain.JoinWithThingsAndTagTb;

public class JoinDao {
    
    private JdbcTemplate jdbcTemplate;

    public JoinDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private RowMapper<JoinWithThingsAndTagTb> joinRowMapper = 
        new RowMapper<JoinWithThingsAndTagTb>() {
            @Override
            public JoinWithThingsAndTagTb mapRow(ResultSet rs, int rowNum) throws SQLException {
                JoinWithThingsAndTagTb joinTb = new JoinWithThingsAndTagTb(
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

    public List<JoinWithThingsAndTagTb> selectByDate(LocalDate date, Long userId) {
        List<JoinWithThingsAndTagTb> results = jdbcTemplate.query(
            "SELECT * FROM things " + 
            "LEFT JOIN things_tag ON things.things_id = things_tag.things_id " + 
            "LEFT JOIN tag ON things_tag.tag_id = tag.tag_id " + 
            "WHERE user_id=? AND DATE(time)=?",
            joinRowMapper,
            userId, date
        );
        return results;
    }
}
