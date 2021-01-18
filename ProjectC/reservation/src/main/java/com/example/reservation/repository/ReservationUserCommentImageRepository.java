package com.example.reservation.repository;

import com.example.reservation.domain.ReservationUserCommentImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
public class ReservationUserCommentImageRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public ReservationUserCommentImageRepository(DataSource dataSource) {
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<ReservationUserCommentImage> findByCommentId(long commentId) {
        Map<String, ?> parameter = Collections.singletonMap("comment_id", commentId);
        return namedParameterJdbcTemplate.query("select * from reservation_user_comment_image where reservation_user_comment_id = :comment_id", parameter, reservationUserCommentImageRowMapper());
    }

    private RowMapper<ReservationUserCommentImage> reservationUserCommentImageRowMapper() {
        return (rs, rowNum) -> {
            ReservationUserCommentImage reservationUserCommentImage = new ReservationUserCommentImage();
            reservationUserCommentImage.setId(rs.getLong("id"));
            reservationUserCommentImage.setReservation_info_id(rs.getLong("reservation_info_id"));
            reservationUserCommentImage.setReservation_user_comment_id(rs.getLong("reservation_user_comment_id"));
            reservationUserCommentImage.setFile_id(rs.getLong("file_id"));
            return reservationUserCommentImage;
        };
    }
}
