package com.bitspilani.coursemasterservice.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class CourseMasterDTO {

    private Long courseId;
    private String courseName;
    private String courseDescription;
    private String courseDuration;
}
