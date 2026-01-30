package com.htmluc.SeniorCare_System.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Entity(name = "tb_user")
public class UserModel
{
    @Id
    private UUID id;

    private String function;
    private String password;

    @OneToOne
    @MapsId
    @JoinColumn(name = "person_id")
    private PersonModel person;

    @OneToMany(mappedBy = "user")
    private List<BandageModel> bandage;
}
