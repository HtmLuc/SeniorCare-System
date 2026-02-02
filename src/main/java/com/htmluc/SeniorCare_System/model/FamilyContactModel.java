package com.htmluc.SeniorCare_System.model;

import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity(name = "tb_family_contact")
public class FamilyContactModel
{
    @Id
    private Long id;

    @NotBlank
    private String relationship;

    @NotBlank
    private String cep;

    @NotBlank
    private String uf;

    @NotBlank
    private String city;

    @NotBlank
    private String neighborhood;

    @NotBlank
    private String road;

    @NotBlank
    private String houseNumber;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id_person")
    private PersonModel person;

    @OneToMany(mappedBy = "familyContact")
    private List<PatientModel> patient;
}
