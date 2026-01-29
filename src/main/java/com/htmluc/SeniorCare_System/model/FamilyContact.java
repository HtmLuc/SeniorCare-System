package com.htmluc.SeniorCare_System.model;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity(name = "tb_family_contact")
public class FamilyContact
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "id_person", nullable = false)
    private Person person;

    private String relationship;
    private String cep;
    private String uf;
    private String city;
    private String neighborhood;
    private String road;
    private String houseNumber;

    @OneToMany(mappedBy = "familyContact")
    private List<Patient> patient;

}
