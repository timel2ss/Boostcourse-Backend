package com.example.reservation.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
@NoArgsConstructor
public class FileInfo {
    private long id;
    private String file_name;
    private String save_file_name;
    private String content_type;
    private boolean delete_flag;
    private Date create_date;
    private Date modify_date;
}
