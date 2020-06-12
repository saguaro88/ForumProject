package com.javaee.projectFroum.projectForum.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportDto {
    private String message;
    private Long topicId;
    private long postId;
}
