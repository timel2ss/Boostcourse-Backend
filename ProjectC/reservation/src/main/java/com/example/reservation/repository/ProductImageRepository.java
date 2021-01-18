package com.example.reservation.repository;

import com.example.reservation.domain.ProductImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ProductImageRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public ProductImageRepository(DataSource dataSource) {
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<ProductImage> findByDisplayId(long displayId) {
        Map<String, ?> parameter = Collections.singletonMap("display_id", displayId);
        return namedParameterJdbcTemplate.query("select pi.id, pi.product_id, pi.type, pi.file_id from display_info d, product p, product_image pi where d.product_id = p.id and p.id = pi.product_id and d.id = :display_id", parameter, productImageRowMapper());
    }

    public long findFileIdByProductId(long productId) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("product_id", productId);
        parameters.put("type", "ma");
        return namedParameterJdbcTemplate.queryForObject("select file_id from product_image where product_id = :product_id and type = :type", parameters, Long.class);
    }

    private RowMapper<ProductImage> productImageRowMapper() {
        return (rs, rowNum) -> {
            ProductImage productImage = new ProductImage();
            productImage.setId(rs.getLong("id"));
            productImage.setProduct_id(rs.getLong("product_id"));
            productImage.setType(rs.getString("type"));
            productImage.setFile_id(rs.getLong("file_id"));
            return productImage;
        };
    }
}
