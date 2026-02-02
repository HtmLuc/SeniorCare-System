package com.htmluc.SeniorCare_System.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
@Entity(name = "tb_patient")
public class PatientModel
{
    @Id
    private Long id;

    private char gender;

    @NotBlank
    private String nationality;

    private int degree_dependence;

    @NotBlank
    private String source_income;

    private String observations;

    @OneToOne(cascade = CascadeType.ALL)
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
