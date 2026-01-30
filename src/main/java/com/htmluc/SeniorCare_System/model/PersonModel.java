package com.htmluc.SeniorCare_System.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity(name = "tb_person")
public class PersonModel
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false, length = 11)
    private String cpf;

    private String name;
    private LocalDate dateBirth;
    private String email;
    private String phoneNumber;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @OneToOne(mappedBy = "person", cascade = CascadeType.ALL)
    private UserModel user;

    @OneToOne(mappedBy = "person", cascade = CascadeType.ALL)
    private PatientModel patient;

    @OneToOne(mappedBy = "person", cascade = CascadeType.ALL)
    private FamilyContactModel familyContact;
}
