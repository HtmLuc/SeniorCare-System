package com.htmluc.SeniorCare_System.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Entity(name = "tb_medicine")
public class MedicineModel
{
    @Id
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String dosage;

    @Column(nullable = false)
    private String frequency;

    @Column(nullable = false)
    private String acquisition;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToMany(mappedBy = "medicines")
    private List<PatientModel> patients;
}