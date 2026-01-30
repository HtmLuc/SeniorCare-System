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


    private String relationship;
    private String cep;
    private String uf;
    private String city;
    private String neighborhood;
    private String road;
    private String houseNumber;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id_person")
    private PersonModel person;

    @OneToMany(mappedBy = "familyContact")
    private List<PatientModel> patient;
}
