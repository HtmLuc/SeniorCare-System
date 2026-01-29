package com.htmluc.SeniorCare_System.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.ManyToAny;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
public class Medicine
{
    @Id
    private UUID id;

    private String name;
    private String dosage;
    private String frequency;
    private String acquisition;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;
}
