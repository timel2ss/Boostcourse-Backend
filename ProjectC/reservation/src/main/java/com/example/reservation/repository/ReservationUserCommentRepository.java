package com.example.reservation.repository;

import com.example.reservation.domain.ReservationUserComment;
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
public class ReservationUserCommentRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public ReservationUserCommentRepository(DataSource dataSource) {
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<Double> getScoreByProductId(long productId) {
        Map<String, ?> parameter = Collections.singletonMap("product_id", productId);
        return namedParameterJdbcTemplate.queryForList("select score from reservation_user_comment where product_id = :product_id", parameter, Double.class);
    }

    public int getTotalCount(long productId) {
        Map<String, ?> parameter = Collections.singletonMap("product_id", productId);
        return namedParameterJdbcTemplate.queryForObject("select count(*) from reservation_user_comment where product_id = :product_id", parameter, Integer.class);
    }

    public List<ReservationUserComment> findByProductId(long productId, int start, int limit) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("product_id", productId);
        parameters.put("start", start);
        parameters.put("limit", limit);
        return namedParameterJdbcTemplate.query("select * from reservation_user_comment where product_id = :product_id limit :start, :limit", parameters, reservationUserCommentRowMapper());
    }

    private RowMapper<ReservationUserComment> reservationUserCommentRowMapper() {
        return (rs, rowNum) -> {
            ReservationUserComment reservationUserComment = new ReservationUserComment();
            reservationUserComment.setId(rs.getLong("id"));
            reservationUserComment.setProduct_id(rs.getLong("product_id"));
            reservationUserComment.setReservation_info_id(rs.getLong("reservation_info_id"));
            reservationUserComment.setUser_id(rs.getLong("user_id"));
            reservationUserComment.setComment(rs.getString("comment"));
            reservationUserComment.setCreate_date(rs.getDate("create_date"));
            reservationUserComment.setModify_date(rs.getDate("modify_date"));
            return reservationUserComment;
        };
    }
}
