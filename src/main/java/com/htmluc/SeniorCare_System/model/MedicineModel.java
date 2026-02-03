package com.htmluc.SeniorCare_System.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity(name = "tb_medicine")
public class MedicineModel
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String dosage;

    @NotBlank
    private String frequency;

    @NotBlank
    private String acquisition;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToMany(mappedBy = "medicines")
    private List<PatientModel> patients;
}