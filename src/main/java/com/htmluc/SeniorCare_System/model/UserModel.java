package com.htmluc.SeniorCare_System.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Entity(name = "tb_user")
public class UserModel
{
    @Id
    private UUID id;

    @NotBlank
    private String function;

    @NotBlank
    private String password;

    @OneToOne
    @MapsId
    @JoinColumn(name = "person_id", nullable = false)
    private PersonModel person;

    @OneToMany(mappedBy = "user")
    private List<BandageModel> bandage;
}
