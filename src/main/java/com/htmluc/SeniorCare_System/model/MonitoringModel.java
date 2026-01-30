package com.htmluc.SeniorCare_System.model;

import jakarta.persistence.*;
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

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDateTime timeMeasure;

    @Column(nullable = false)
    private String bloodPressure;

    @Column(nullable = false)
    private String heartRate;

    @Column(nullable = false)
    private String respiratoryRate;

    @Column(nullable = false)
    private String saturation;

    @Column(nullable = false)
    private String temperature;

    @Column(nullable = false)
    private String bloodSugar;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private PatientModel patient;
}
