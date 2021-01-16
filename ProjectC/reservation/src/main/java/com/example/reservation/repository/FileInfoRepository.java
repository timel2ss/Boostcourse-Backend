package com.example.reservation.repository;

import com.example.reservation.domain.FileInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.Map;

@Repository
public class FileInfoRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public FileInfoRepository(DataSource dataSource) {
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public FileInfo findById(long id) {
        Map<String, ?> parameter = Collections.singletonMap("id", id);
        return namedParameterJdbcTemplate.queryForObject("select * from file_info where id = :id", parameter, fileInfoRowMapper());
    }

    private RowMapper<FileInfo> fileInfoRowMapper() {
        return (rs, rowNum) -> {
            FileInfo fileInfo = new FileInfo();
            fileInfo.setId(rs.getLong("id"));
            fileInfo.setFile_name(rs.getString("file_name"));
            fileInfo.setSave_file_name(rs.getString("save_file_name"));
            fileInfo.setContent_type(rs.getString("content_type"));
            fileInfo.setDelete_flag(rs.getBoolean("delete_flag"));
            fileInfo.setCreate_date(rs.getDate("create_date"));
            fileInfo.setModify_date(rs.getDate("modify_date"));
            return fileInfo;
        };
    }
}
