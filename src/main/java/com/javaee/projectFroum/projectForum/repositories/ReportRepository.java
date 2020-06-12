package com.javaee.projectFroum.projectForum.repositories;

import com.javaee.projectFroum.projectForum.models.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
}
