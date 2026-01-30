package com.htmluc.SeniorCare_System.model;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "tb_family_contact")
public class FamilyContactModel
{
    @Id
    private UUID id;

    @Column(nullable = false)
    private String relationship;

    @Column(nullable = false)
    private String cep;

    @Column(nullable = false)
    private String uf;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String neighborhood;

    @Column(nullable = false)
    private String road;

    @Column(nullable = false)
    private String houseNumber;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id_person")
    private PersonModel person;

    @OneToMany(mappedBy = "familyContact")
    private List<PatientModel> patient;
}
