package com.javaee.projectFroum.projectForum.controllers;

import com.javaee.projectFroum.projectForum.dto.ReportDto;
import com.javaee.projectFroum.projectForum.dto.ReportMapper;
import com.javaee.projectFroum.projectForum.dto.TopicDto;
import com.javaee.projectFroum.projectForum.dto.TopicMapper;
import com.javaee.projectFroum.projectForum.exceptions.WrongUsernameException;
import com.javaee.projectFroum.projectForum.services.ReportServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@Controller
@RequestMapping("/report")
@Slf4j
public class ReportController {

    @Autowired
    ReportServiceImpl reportService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(method = RequestMethod.GET)
    public String getAllReports(ModelMap model) {
        model.addAttribute("reportList", reportService.getAllReports());
        log.info("All reports page returned.");
        return "reports";
    }

    @GetMapping(path = "add/{topicId}")
    public String addTopicReport(Model model) {
        model.addAttribute("reportForm", new ReportDto());
        log.info("Add topic report page returned.");
        return "report";
    }
    @PostMapping(path = "add/{topicId}")
    public String addTopicReport(@ModelAttribute("reportForm") ReportDto reportDto) throws ParseException {
        reportService.createReport(ReportMapper.toReport(reportDto));
        log.info("Topic report added.");
        return "redirect:/topic";
    }
    @GetMapping(path = "add/{topicId}/{postId}")
    public String addPostReport(Model model) {
        model.addAttribute("reportForm", new ReportDto());
        log.info("Post report page returned.");
        return "report";
    }
    @PostMapping(path = "add/{topicId}/{postId}")
    public String addPostReport(@ModelAttribute("reportForm") ReportDto reportDto) throws ParseException {
        reportService.createReport(ReportMapper.toReport(reportDto));
        log.info("Post report added.");
        return "redirect:/topic/{topicId}";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(path = "delete/{id}")
    public String deleteReport(@PathVariable("id") long id) throws WrongUsernameException {
        reportService.deleteReportById(id);
        log.info("Report deleted.");
        return "redirect:/report";
    }
}
