package com.htmluc.SeniorCare_System.model;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;

@Data
@Entity(name = "tb_bandage")
public class BandageModel
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private LocalDateTime medicationTime;

    @Column(nullable = false)
    private String bandageType;

    @Column(nullable = false)
    private String exudateQuantity;

    @Column(nullable = false)
    private String exudateType;

    @Column(nullable = false)
    private String edges;

    @Column(nullable = false)
    private String bed;

    @Column(nullable = false)
    private String cleaningSupplies;

    @Column(nullable = false)
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
