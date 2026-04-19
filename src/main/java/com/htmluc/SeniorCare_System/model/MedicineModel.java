package com.htmluc.SeniorCare_System.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    @ManyToMany(mappedBy = "medicines")
    private List<PatientModel> patients;
}