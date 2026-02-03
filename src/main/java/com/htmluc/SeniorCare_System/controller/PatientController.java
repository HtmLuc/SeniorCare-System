package com.htmluc.SeniorCare_System.controller;

import com.htmluc.SeniorCare_System.model.FamilyContactModel;
import com.htmluc.SeniorCare_System.model.PatientModel;
import com.htmluc.SeniorCare_System.repository.PatientRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/patients")
@Tag(name = "Patient", description = "Endpoints for managing patient data")
public class PatientController
{
    @Autowired
    private PatientRepository patientRepository;

    @GetMapping
    @Operation(summary = "List all patients", description = "Retrieves a comprehensive list of all registered patients from the database.", deprecated = false)
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of patient")
    @ApiResponse(responseCode = "204", description = "No patients found in the database")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<List<PatientModel>> listAll()
    {
        List<PatientModel> allPatients = this.patientRepository.findAll();
        if (allPatients.isEmpty())
        {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(allPatients);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get patient by ID", description = "Retrieves a patient by its unique identifier")
    @ApiResponse(responseCode = "200", description = "Patient retrieved successfully")
    @ApiResponse(responseCode = "404", description = "patient not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<PatientModel> getById(@PathVariable Long id)
    {
        return this.patientRepository.findById(id).map(patient -> ResponseEntity.ok(patient)).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update patient by ID", description = "Updates an existing patient with the provided data")
    @ApiResponse(responseCode = "200", description = "Patient updated successfully")
    @ApiResponse(responseCode = "404", description = "Patient not found")
    @ApiResponse(responseCode = "400", description = "Invalid input data")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<PatientModel> updateById(@PathVariable Long id, @RequestBody PatientModel patientModel)
    {
        return this.patientRepository.findById(id).map(info -> {
            info.setGender(patientModel.getGender());
            info.setDegree_dependence(patientModel.getDegree_dependence());
            info.setObservations(patientModel.getObservations());

            PatientModel patient = this.patientRepository.save(info);
            return ResponseEntity.ok(patient);
        }).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Save patient data", description = "Method for saving patient data.", deprecated = false)
    @ApiResponse(responseCode = "201", description = "Patient created successfully")
    @ApiResponse(responseCode = "409", description = "Patient already exists")
    @ApiResponse(responseCode = "500", description = "Internal server error")
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

    @DeleteMapping("{id}")
    @Operation(summary = "Delete patient data", description = "Method for delete patient data.", deprecated = false)
    @ApiResponse(responseCode = "204", description = "Patient deleted successfully")
    @ApiResponse(responseCode = "404", description = "Patient not found")
    public ResponseEntity<Void> delete(@PathVariable Long id)
    {
        if (!this.patientRepository.existsById(id))
        {
            return ResponseEntity.notFound().build();
        }
        this.patientRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
