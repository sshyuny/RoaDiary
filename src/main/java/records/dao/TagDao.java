package records.dao;

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

import domain.TagDto;

public class TagDao {
    
    private JdbcTemplate jdbcTemplate;

    public TagDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private RowMapper<TagDto> tagRowMapper = 
        new RowMapper<TagDto>() {
            @Override
            public TagDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                TagDto tagTb = new TagDto(
                    rs.getString("name")
                );
                tagTb.setTagID(rs.getInt("tag_id"));
                return tagTb;
            }
        };

    public void insert(final TagDto tagDto) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator pre = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement prstmt = con.prepareStatement(
                    "INSERT INTO tag(name) " + 
                    "VALUES (?)",
                    new String[] {"tag_id"}
                );
                prstmt.setString(1, tagDto.getTagContent());
                return prstmt;
            }
        };
        jdbcTemplate.update(pre, keyHolder);
        Number keyValue = keyHolder.getKey();
        tagDto.setTagID(keyValue.intValue());
    }

    public TagDto selectByName(String name) {
        List<TagDto> result = jdbcTemplate.query(
            "SELECT * FROM tag WHERE name = ?",
            tagRowMapper, name);
        return result.isEmpty() ? null : result.get(0);
    }
}
