package com.example.reservation.repository;

import com.example.reservation.domain.Promotion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;

@Repository
public class PromotionRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public PromotionRepository(DataSource dataSource) {
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }
    /* TODO
        size : 프로모션 정보의 수
        items : 프로모션 상품 정보
        fileId : file_info 테이블의 id (product_image의 타입중 ma인 경우만)
     */

    public long getNumberOfPromotions() {
        return namedParameterJdbcTemplate.queryForObject("select count(*) from promotion", Collections.emptyMap(), Long.class);
    }

    public List<Promotion> findAll() {
        return namedParameterJdbcTemplate.query("select * from promotion", Collections.emptyMap(), promotionRowMapper());
    }

    private RowMapper<Promotion> promotionRowMapper() {
        return (rs, rowNum) -> {
            Promotion promotion = new Promotion();
            promotion.setId(rs.getLong("id"));
            promotion.setProduct_id(rs.getLong("product_id"));
            return promotion;
        };
    }
}
