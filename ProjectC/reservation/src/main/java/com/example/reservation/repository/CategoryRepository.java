package com.example.reservation.repository;

import com.example.reservation.domain.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
public class CategoryRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public CategoryRepository(DataSource dataSource) {
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public Long getCategoriesSize() {
        Long result = namedParameterJdbcTemplate.queryForObject("select count(*) from category", Collections.emptyMap(), Long.class);
        if(result != null) {
            return result;
        }
        return 0L;
    }

    public Long getCategoryCountById(Long id) {
        Map<String, ?> parameter = Collections.singletonMap("id", id);
        Long result = namedParameterJdbcTemplate.queryForObject("select count(*) from category c, product p where c.id = p.category_id and c.id = :id", parameter, Long.class);
        if(result != null) {
            return result;
        }
        return 0L;
    }

    public List<Category> findAllCategories() {
        return namedParameterJdbcTemplate.query("select id, name from Category", Collections.emptyMap(), categoryRowMapper());
    }

    public Category findById(long id) {
        Map<String, ?> parameter = Collections.singletonMap("id", id);
        return namedParameterJdbcTemplate.queryForObject("select * from category where id = :id", parameter, categoryRowMapper());
    }

    private RowMapper<Category> categoryRowMapper() {
        return (rs, rowNum) -> {
            Category category = new Category();
            category.setId(rs.getLong("id"));
            category.setName(rs.getString("name"));
            return category;
        };
    }
}
