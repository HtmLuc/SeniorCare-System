package com.htmluc.SeniorCare_System.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity(name = "tb_monitoring")
public class MonitoringModel
{
    @Id
    private Long id;

    @NotBlank
    private String name;

    private LocalDateTime timeMeasure;

    @NotBlank
    private String bloodPressure;

    @NotBlank
    private String heartRate;

    @NotBlank
    private String respiratoryRate;

    @NotBlank
    private String saturation;

    @NotBlank
    private String temperature;

    @NotBlank
    private String bloodSugar;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private PatientModel patient;
}
