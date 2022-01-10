package records;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import domain.TagTb;

public class TagDao {
    
    private JdbcTemplate jdbcTemplate;

    public TagDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private RowMapper<TagTb> tagRowMapper = 
        new RowMapper<TagTb>() {
            @Override
            public TagTb mapRow(ResultSet rs, int rowNum) throws SQLException {
                TagTb tagTb = new TagTb(
                    rs.getString("name")
                );
                tagTb.setTagID(rs.getInt("tag_id"));
                return tagTb;
            }
        };

    public void insert(final TagTb tagTb) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator pre = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement prstmt = con.prepareStatement(
                    "INSERT INTO tag(name) " + 
                    "VALUES (?)",
                    new String[] {"tag_id"}
                );
                prstmt.setString(1, tagTb.getTagContent());
                return prstmt;
            }
        };
        jdbcTemplate.update(pre, keyHolder);
        Number keyValue = keyHolder.getKey();
        tagTb.setTagID(keyValue.intValue());
    }

    public TagTb selectByName(String name) {
        List<TagTb> result = jdbcTemplate.query(
            "SELECT * FROM tag WHERE name = ?",
            tagRowMapper, name);
        return result.isEmpty() ? null : result.get(0);
    }
}
