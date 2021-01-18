package com.example.reservation.repository;

import com.example.reservation.domain.ProductPrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
public class ProductPriceRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public ProductPriceRepository(DataSource dataSource) {
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<ProductPrice> findByProductId(long productId) {
        Map<String, ?> parameter = Collections.singletonMap("product_id", productId);
        return namedParameterJdbcTemplate.query("select * from product_price where product_id = :product_id", parameter, productPriceRowMapper());
    }

    private RowMapper<ProductPrice> productPriceRowMapper() {
        return (rs, rowNum) -> {
            ProductPrice productPrice = new ProductPrice();
            productPrice.setId(rs.getLong("id"));
            productPrice.setProduct_id(rs.getLong("product_id"));
            productPrice.setPrice_type_name(rs.getString("price_type_name"));
            productPrice.setPrice(rs.getLong("price"));
            productPrice.setDiscount_rate(rs.getDouble("discount_rate"));
            productPrice.setCreate_date(rs.getDate("create_date"));
            productPrice.setModify_date(rs.getDate("modify_date"));
            return productPrice;
        };
    }
}
