package com.example.guestbook.repository;

import com.example.guestbook.domain.Guestbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class GuestbookRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    @Autowired
    public GuestbookRepository(DataSource dataSource) {
         namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
         jdbcInsert = new SimpleJdbcInsert(dataSource);
         jdbcInsert.withTableName("guestbook").usingGeneratedKeyColumns("id");
    }

    public Guestbook write(Guestbook guestbook) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", guestbook.getName());
        parameters.put("content", guestbook.getContent());
        parameters.put("regdate", guestbook.getRegdate());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        guestbook.setId(key.intValue());
        return guestbook;
    }

    public int delete(long id) {
        Map<String, ?> parameters = Collections.singletonMap("id", id);
        return namedParameterJdbcTemplate.update("delete from guestbook where id = :id", parameters);
    }

    public List<Guestbook> getList(int start, int limit) {
        Map<String, Integer> parameters = new HashMap<>();
        parameters.put("start", start);
        parameters.put("limit", limit);
        return namedParameterJdbcTemplate.query("select * from guestbook limit :start, :limit", parameters, guestbookRowMapper());
    }

    public int getCount() {
        Integer result = namedParameterJdbcTemplate.queryForObject("select count(*) from guestbook", Collections.emptyMap(), Integer.class);
        if(result != null) {
            return result;
        }
        return 0;
    }

    private RowMapper<Guestbook> guestbookRowMapper() {
        return (rs, rowNum) -> {
            Guestbook guestbook = new Guestbook();
            guestbook.setId(rs.getLong("id"));
            guestbook.setName(rs.getString("name"));
            guestbook.setContent(rs.getString("content"));
            guestbook.setRegdate(rs.getDate("regdate"));
            return guestbook;
        };
    }
}
