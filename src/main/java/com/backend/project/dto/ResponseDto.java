package com.backend.project.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseDto {
    private String name;
    private String path;
    private String sha;
    private Long size;
    private String url;
    private String html_url;
    private String git_url;
    private String download_url;
    private String type;
}
