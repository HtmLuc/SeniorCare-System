package com.htmluc.SeniorCare_System.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity(name = "tbUser")
public class User {
    @Id
    private UUID id;

    private String function;
    private String password;

    @OneToOne
    @MapsId
    @JoinColumn(name = "person_id")
    private Person person;
}
