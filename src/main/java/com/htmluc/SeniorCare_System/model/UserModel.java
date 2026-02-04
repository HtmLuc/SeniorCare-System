package com.htmluc.SeniorCare_System.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity(name = "tb_user")
public class UserModel
{
    @Id
    private Long id;

    @NotBlank
    private String function;

    @NotBlank
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "person_id", nullable = false)
    private PersonModel person;
}
