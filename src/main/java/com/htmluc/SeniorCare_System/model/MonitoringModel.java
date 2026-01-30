package com.htmluc.SeniorCare_System.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity(name = "tb_monitoring")
public class MonitoringModel
{
    @Id
    private UUID id;

    private String name;
    private LocalDateTime timeMeasure;
    private String bloodPressure;
    private String heartRate;
    private String respiratoryRate;
    private String saturation;
    private String temperature;
    private String bloodSugar;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private PatientModel patient;
}
