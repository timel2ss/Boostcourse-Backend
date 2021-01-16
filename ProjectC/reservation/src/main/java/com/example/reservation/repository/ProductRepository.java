package com.example.reservation.repository;

import com.example.reservation.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
public class ProductRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public ProductRepository(DataSource dataSource) {
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<Product> findAll() {
        return namedParameterJdbcTemplate.query("select * from product", Collections.emptyMap(), productRowMapper());
    }

    public Product findById(long id) {
        Map<String, ?> parameter = Collections.singletonMap("id", id);
        return namedParameterJdbcTemplate.queryForObject("select * from product where id = :id", parameter, productRowMapper());
    }

    public Product findByDisplayId(long id) {
        Map<String, ?> parameter = Collections.singletonMap("id", id);
        return namedParameterJdbcTemplate.queryForObject("select p.id, p.category_id, p.description, p.content, p.event, p.create_date, p.modify_date from product p , display_info d where p.id = d.product_id and d.id = :id", parameter, productRowMapper());
    }

    public List<Product> findByCategoryId(long categoryId) {
        Map<String, ?> parameter = Collections.singletonMap("category_id", categoryId);
        return namedParameterJdbcTemplate.query("select * from product where category_id = :category_id", parameter, productRowMapper());
    }

    private RowMapper<Product> productRowMapper() {
        return (rs, rowNum) -> {
            Product product = new Product();
            product.setId(rs.getLong("id"));
            product.setCategory_id(rs.getLong("category_id"));
            product.setDescription(rs.getString("description"));
            product.setContent(rs.getString("content"));
            product.setEvent(rs.getString("event"));
            product.setCreate_date(rs.getDate("create_date"));
            product.setModify_date(rs.getDate("modify_date"));
            return product;
        };
    }
}
