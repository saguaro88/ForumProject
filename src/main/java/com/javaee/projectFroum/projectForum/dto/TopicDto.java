package com.javaee.projectFroum.projectForum.dto;

import lombok.Getter;
import lombok.Setter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
public class TopicDto {
    private String username;
    private String title;
    private String firstPostContent;

    public TopicDto(){

    }

}

