package com.example.reservation.repository;

import com.example.reservation.domain.DisplayInfoImage;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
public class DisplayInfoImageRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public DisplayInfoImageRepository(DataSource dataSource) {
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public Long findFileIdByDisplayInfoId(long displayInfoId) {
        Map<String, ?> parameter = Collections.singletonMap("displayInfoId", displayInfoId);
        return namedParameterJdbcTemplate.queryForObject("select file_id from display_info_image where display_info_id = :displayInfoId", parameter, Long.class);
    }
}
