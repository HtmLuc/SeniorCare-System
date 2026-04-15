package com.htmluc.SeniorCare_System.model;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionModel
{
    private Instant timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;
}