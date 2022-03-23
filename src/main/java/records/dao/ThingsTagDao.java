package records.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;

import domain.ThingsTagDto;

public class ThingsTagDao {
    
    private JdbcTemplate jdbcTemplate;

    public ThingsTagDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(final ThingsTagDto thingsTagDto) {
        PreparedStatementCreator pre = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement prstmt = con.prepareStatement(
                    "INSERT INTO things_tag(things_id, tag_id) " + 
                    "VALUES (?, ?)"
                );
                prstmt.setLong(1, thingsTagDto.getThingsId());
                prstmt.setInt(2, thingsTagDto.getTagId());
                return prstmt;
            }
        };
        jdbcTemplate.update(pre);
    }

    // 태그 변경 시, 기존 tag_id 기록된 부분 삭제
    public void delete(Long thingsId) {
        jdbcTemplate.update(
            "DELETE FROM things_tag WHERE things_id = ?",
            thingsId
        );
    }

}
