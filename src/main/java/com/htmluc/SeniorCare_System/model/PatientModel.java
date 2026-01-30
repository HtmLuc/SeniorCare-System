package com.htmluc.SeniorCare_System.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Entity(name = "tb_patient")
public class PatientModel
{
    @Id
    private UUID id;

    @Column(nullable = false)
    private char gender;

    @Column(nullable = false)
    private String nationality;

    @Column(nullable = false)
    private int degree_dependence;

    @Column(nullable = false)
    private String source_income;

    private String observations;

    @OneToOne
    @MapsId
    @JoinColumn(name = "person_id")
    private PersonModel person;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<BandageModel> bandage;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<MonitoringModel> monitorings;
  
    @ManyToOne
    @JoinColumn(name = "family_contact_id")
    private FamilyContactModel familyContact;

    @ManyToMany
    @JoinTable(name = "tb_patient_medicine",
            joinColumns = @JoinColumn(name = "patient_id"),
            inverseJoinColumns = @JoinColumn(name = "medicine_id")
    )
    private List<MedicineModel> medicines;
}
