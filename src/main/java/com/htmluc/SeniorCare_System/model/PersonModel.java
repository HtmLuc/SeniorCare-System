package com.htmluc.SeniorCare_System.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity(name = "tb_person")
public class PersonModel
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 11)
    private String cpf;

    @NotBlank
    private String name;

    private LocalDate dateBirth;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String phoneNumber;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @OneToOne
    @JsonIgnore
    private UserModel user;

    @OneToOne
    private PatientModel patient;

    @OneToOne
    private FamilyContactModel familyContact;
}
