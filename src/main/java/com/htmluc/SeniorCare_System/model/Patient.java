package com.htmluc.SeniorCare_System.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Entity(name = "tb_patient")
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

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<Monitoring> monitorings;
  
    @ManyToOne
    @JoinColumn(name = "familyContact")
    private FamilyContact familyContact;

    @ManyToMany
    @JoinTable(name = "tb_patient_medicine",
            joinColumns = @JoinColumn(name = "patient_id"),
            inverseJoinColumns = @JoinColumn(name = "medicine_id")
    )
    private List<Medicine> medicines;
}
