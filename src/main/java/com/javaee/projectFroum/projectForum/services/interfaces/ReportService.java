package com.javaee.projectFroum.projectForum.services.interfaces;

import com.javaee.projectFroum.projectForum.models.Report;

import java.util.List;
import java.util.Optional;

public interface ReportService {
    Report getReportById(long id);
    List<Report> getAllReports();
    Report createReport(Report report);
    boolean deleteReportById(long id);
}
