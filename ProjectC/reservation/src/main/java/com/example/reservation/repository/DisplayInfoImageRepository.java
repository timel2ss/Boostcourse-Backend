package com.example.reservation.repository;

import com.example.reservation.domain.DisplayInfoImage;
import org.springframework.jdbc.core.RowMapper;
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
        Map<String, ?> parameter = Collections.singletonMap("display_info_id", displayInfoId);
        return namedParameterJdbcTemplate.queryForObject("select file_id from display_info_image where display_info_id = :displa_info_id", parameter, Long.class);
    }

    public DisplayInfoImage findByDisplayId(long displayId) {
        Map<String, ?> parameter = Collections.singletonMap("display_Id", displayId);
        return namedParameterJdbcTemplate.queryForObject("select * from display_info_image where display_info_id = :display_id", parameter, displayInfoImageRowMapper());
    }

    private RowMapper<DisplayInfoImage> displayInfoImageRowMapper() {
        return (rs, rowNum) -> {
            DisplayInfoImage displayInfoImage = new DisplayInfoImage();
            displayInfoImage.setId(rs.getLong("id"));
            displayInfoImage.setDisplay_info_id(rs.getLong("display_info_id"));
            displayInfoImage.setFile_id(rs.getLong("file_id"));
            return displayInfoImage;
        };
    }
}
