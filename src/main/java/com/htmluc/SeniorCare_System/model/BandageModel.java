package com.htmluc.SeniorCare_System.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity(name = "tb_bandage")
public class BandageModel
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private LocalDateTime medicationTime;
    private String bandageType;
    private String exudateQuantity;
    private String exudateType;
    private String edges;
    private String bed;
    private String cleaningSupplies;
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
