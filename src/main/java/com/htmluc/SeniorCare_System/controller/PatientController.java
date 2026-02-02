package com.htmluc.SeniorCare_System.controller;

import com.htmluc.SeniorCare_System.model.PatientModel;
import com.htmluc.SeniorCare_System.repository.PatientRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/patients")
@Tag(name = "Patient", description = "Endpoints for managing patient data")
public class PatientController
{
    @Autowired
    private PatientRepository patientRepository;

    @PostMapping
    @Operation(summary = "Save patient data", description = "Method for saving user data.", deprecated = false)
    @ApiResponse(responseCode = "201", description = "User created successfully")
    @ApiResponse(responseCode = "409", description = "User already exists")
    public ResponseEntity<PatientModel> create(@RequestBody PatientModel patientModel)
    {
        if (patientRepository.existsByPersonCpf(patientModel.getPerson().getCpf()))
        {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        patientModel.setId(null);
        PatientModel patient = patientRepository.save(patientModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(patient);
    }
}
