package com.javaee.projectFroum.projectForum.services;

import com.javaee.projectFroum.projectForum.models.Report;
import com.javaee.projectFroum.projectForum.repositories.ReportRepository;
import com.javaee.projectFroum.projectForum.services.interfaces.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ReportServiceImpl implements ReportService {
    @Autowired
    ReportRepository reportRepository;

    @Override
    public Report getReportById(long id) {
        log.info("Getting report by id.");
        Optional<Report> optionalReport = reportRepository.findById(id);
        return optionalReport.orElse(null);
    }

    @Override
    public List<Report> getAllReports() {
        log.info("Getting all reports.");
        return reportRepository.findAll();
    }

    @Override
    public Report createReport(Report report) {
        log.info("Creating report.");
        return reportRepository.save(report);
    }

    @Override
    public boolean deleteReportById(long id) {
        log.info("Deleting report by ID.");
        Optional<Report> optionalReport = reportRepository.findById(id);
            if(optionalReport.isPresent()){
                reportRepository.deleteById(id);
                log.info("Succesfuly deleted report.");
                return true;
            }else
            log.error("Error while deleting report.");
             return false;
    }
}
