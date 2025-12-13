package com.mybury.waver.repository;

import com.mybury.waver.common.code.ReportType;
import com.mybury.waver.domain.Report;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReportRepository extends JpaRepository<Report, Long> {

  @Query("""
      SELECT r.bucketlistId
      FROM Report r
      WHERE r.reportUserId = :reportUserId
        AND r.reportType = :reportType
        AND r.bucketlistId IS NOT NULL
      """)
  List<Long> findBucketlistIdsByReportUserIdAndReportType(
      @Param("reportUserId") Long reportUserId,
      @Param("reportType") ReportType reportType
  );
}
