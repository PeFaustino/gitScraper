package com.backend.project.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FilesDto {
    private String extension;
    private Integer count;
    private Long lines;
    private Long bytes;

    public FilesDto(String extension, Integer count, Long lines, Long bytes) {
        this.extension = extension;
        this.count = count;
        this.lines = lines;
        this.bytes = bytes;
    }
}
