package com.javaee.projectFroum.projectForum.dto;

import com.javaee.projectFroum.projectForum.models.Post;
import com.javaee.projectFroum.projectForum.models.Report;
import com.javaee.projectFroum.projectForum.services.PostServiceImpl;
import com.javaee.projectFroum.projectForum.services.TopicServiceImpl;
import com.javaee.projectFroum.projectForum.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;

@Controller
public class ReportMapper {
    private static PostServiceImpl postService;
    private static UserServiceImpl userService;
    private static TopicServiceImpl topicService;

    @Autowired
    public ReportMapper(PostServiceImpl postService, UserServiceImpl userService, TopicServiceImpl topicService){
        ReportMapper.postService = postService;
        ReportMapper.userService = userService;
        ReportMapper.topicService = topicService;
    }

    public static Report toReport(ReportDto reportDto){
        Report report = new Report();
        report.setMessage(reportDto.getMessage());
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails)principal).getUsername();
        report.setReportedByUser(userService.findByUsername(username));
        report.setReportedPost(postService.getPostById(reportDto.getPostId()));
        report.setReportedTopic(topicService.getTopicById(reportDto.getTopicId()));
        return report;
    }

}