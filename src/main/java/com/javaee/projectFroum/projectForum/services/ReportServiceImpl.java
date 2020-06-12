package com.javaee.projectFroum.projectForum.services;

import com.javaee.projectFroum.projectForum.models.Report;
import com.javaee.projectFroum.projectForum.repositories.ReportRepository;
import com.javaee.projectFroum.projectForum.services.interfaces.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    ReportRepository reportRepository;

    @Override
    public Report getReportById(long id) {
        Optional<Report> optionalReport = reportRepository.findById(id);
        return optionalReport.orElse(null);
    }

    @Override
    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }

    @Override
    public Report createReport(Report report) {
        return reportRepository.save(report);
    }

    @Override
    public boolean deleteReportById(long id) {
        Optional<Report> optionalReport = reportRepository.findById(id);
            if(optionalReport.isPresent()){
                reportRepository.deleteById(id);
                return true;
            }
            else return false;
    }
}
