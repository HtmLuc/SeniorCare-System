package com.htmluc.SeniorCare_System.repository;

import com.htmluc.SeniorCare_System.model.MonitoringModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MonitoringRepository extends JpaRepository<MonitoringModel, Long>
{
    @Query("SELECT mo FROM tb_monitoring mo WHERE mo.patient.id = :patientId ORDER BY mo.createdAt DESC")
    Page<MonitoringModel> findHistoryByPatient(@Param("id") Long patientId, Pageable pageable);
}
