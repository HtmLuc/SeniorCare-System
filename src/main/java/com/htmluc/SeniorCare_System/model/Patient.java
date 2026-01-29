package com.htmluc.SeniorCare_System.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
public class Patient
{
    @Id
    private UUID id;

    private char gender;
    private String nationality;
    private int degree_dependence;
    private String source_income;
    private String observations;

    @OneToOne
    @MapsId
    @JoinColumn(name = "person_id")
    private Person person;
}
