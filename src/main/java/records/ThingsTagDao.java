package records;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;

import domain.ThingsTagTb;

public class ThingsTagDao {
    
    private JdbcTemplate jdbcTemplate;

    public ThingsTagDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(final ThingsTagTb thingsTagTb) {
        PreparedStatementCreator pre = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement prstmt = con.prepareStatement(
                    "INSERT INTO things_tag(things_id, tag_id) " + 
                    "VALUES (?, ?)"
                );
                prstmt.setLong(1, thingsTagTb.getThingsId());
                prstmt.setInt(2, thingsTagTb.getTagId());
                return prstmt;
            }
        };
        jdbcTemplate.update(pre);
    }

}
