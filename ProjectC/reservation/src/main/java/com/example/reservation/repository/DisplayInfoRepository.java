package com.example.reservation.repository;

import com.example.reservation.domain.DisplayInfo;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
public class DisplayInfoRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public DisplayInfoRepository(DataSource dataSource) {
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<DisplayInfo> findByProductId(long productId) {
        Map<String, ?> parameter = Collections.singletonMap("product_id", productId);
        return namedParameterJdbcTemplate.query("select * from display_info where product_id = :product_id", parameter, displayInfoRowMapper());
    }

    public long getDisplayInfoCountByCategoryId(long categoryId) {
        Map<String, ?> parameter = Collections.singletonMap("category_id", categoryId);
        return namedParameterJdbcTemplate.queryForObject("select count(*) from product p, display_info d where p.id = d.product_id and p.category_id = :category_id", parameter, Long.class);
    }

    private RowMapper<DisplayInfo> displayInfoRowMapper() {
        return (rs, rowNum) -> {
            DisplayInfo displayInfo = new DisplayInfo();
            displayInfo.setId(rs.getLong("id"));
            displayInfo.setProduct_id(rs.getLong("product_id"));
            displayInfo.setOpening_hours(rs.getString("opening_hours"));
            displayInfo.setPlace_name(rs.getString("place_name"));
            displayInfo.setPlace_lot(rs.getString("place_lot"));
            displayInfo.setPlace_street(rs.getString("place_street"));
            displayInfo.setTel(rs.getString("tel"));
            displayInfo.setHomepage(rs.getString("homepage"));
            displayInfo.setEmail(rs.getString("email"));
            displayInfo.setCreate_date(rs.getDate("create_date"));
            displayInfo.setModify_date(rs.getDate("modify_date"));
            return displayInfo;
        };
    }
    /* TODO
        totalCount : 해당 카테고리의 전시 상품 수
        productCount : 읽어온 전시 상품 수
        products : 전시 상품 정보

       TODO
        Details
        product : 상품정보
        productImages : 상품 이미지 정보들
        displayInfoImages : 전시 이미지 정보들
        avgScore : 댓글 점수의 평균 (int값)
        productPrices : 상품 가격 정보들
     */
}
