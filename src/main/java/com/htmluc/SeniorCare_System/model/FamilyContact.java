package com.htmluc.SeniorCare_System.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Table(name = "tb_family_contact")
public class FamilyContact
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
}
