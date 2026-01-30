package com.htmluc.SeniorCare_System.model;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;

@Data
@Entity(name = "tb_bandage")
public class BandageModel
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank
    private LocalDateTime medicationTime;

    @NotBlank
    private String bandageType;

    @NotBlank
    private String exudateQuantity;

    @NotBlank
    private String exudateType;

    @NotBlank
    private String edges;

    @NotBlank
    private String bed;

    @NotBlank
    private String cleaningSupplies;

    @NotBlank
    private String coverage;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private PatientModel patient;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserModel user;
}
