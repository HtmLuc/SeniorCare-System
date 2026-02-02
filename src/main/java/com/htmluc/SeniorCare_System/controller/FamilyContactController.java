package com.htmluc.SeniorCare_System.controller;

import com.htmluc.SeniorCare_System.model.FamilyContactModel;
import com.htmluc.SeniorCare_System.repository.FamilyContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/v1/familyContact")
@Tag(name = "FamilyContact", description = "Endpoints for managing familyContact data")
public class FamilyContactController 
{
    @Autowired
    private FamilyContactRepository familyContactRepository;

    @GetMapping("/patient/{idPatient}/familyContact")
    @Operation(summary = "Get family contact by patient ID", description = "Retrieve the family contact information associated with a specific patient")
    @ApiResponse(responseCode = "200", description = "Family contact found successfully")
    @ApiResponse(responseCode = "404", description = "Patient not found or has no family contact associated")

    public ResponseEntity<FamilyContactModel> getFamilyContactByPatientId(
        @Parameter(description = "patient ID", required = true) 
        @PathVariable Long idPatient){
        return familyContactRepository.findById(idPatient)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    } 

    @PostMapping("/familyContact")
    @Operation(summary = "Create a new family contact", 
               description = "Creates a new family contact with the provided data")
    @ApiResponse(responseCode = "201", description = "Family contact created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input data")
    public ResponseEntity<FamilyContactModel> criar(@RequestBody FamilyContactModel familyContactModel) {
        
        FamilyContactModel savedContact = familyContactRepository.save(familyContactModel);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(savedContact);
    }
}
