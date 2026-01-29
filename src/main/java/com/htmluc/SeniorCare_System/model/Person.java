package com.htmluc.SeniorCare_System.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity(name = "tbPerson")
public class Person {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(unique = true)
    private int cpf;

    private String name;
    private LocalDate dateBirth;
    private String email;
    private int phoneNumber;

    private LocalDateTime createdAt;
}
