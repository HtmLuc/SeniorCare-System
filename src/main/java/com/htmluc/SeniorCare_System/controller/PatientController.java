package com.htmluc.SeniorCare_System.controller;

import com.htmluc.SeniorCare_System.exception.ResourceNotFoundException;
import com.htmluc.SeniorCare_System.model.MedicineModel;
import com.htmluc.SeniorCare_System.model.MonitoringModel;
import com.htmluc.SeniorCare_System.model.PatientModel;
import com.htmluc.SeniorCare_System.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/patients")
@Tag(name = "Patient", description = "Endpoints for managing patient data")
public class PatientController
{
    private final PatientService patientService;

    @GetMapping
    @Operation(summary = "List all patients", description = "Retrieves a comprehensive list of all registered patients from the database.", deprecated = false)
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of patient")
    @ApiResponse(responseCode = "204", description = "No patients found in the database")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<Page<PatientModel>> listAll(Pageable pageable)
    {
        Page<PatientModel> patients = patientService.listAll(pageable);
        if (patients.isEmpty())
        {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(patients);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get patient by ID", description = "Retrieves a patient by its unique identifier")
    @ApiResponse(responseCode = "200", description = "Patient retrieved successfully")
    @ApiResponse(responseCode = "404", description = "patient not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<PatientModel> getById(@PathVariable Long id)
    {
        return this.patientService.findById(id)
                .map(patient -> ResponseEntity.ok(patient))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update patient by ID", description = "Updates an existing patient with the provided data")
    @ApiResponse(responseCode = "200", description = "Patient updated successfully")
    @ApiResponse(responseCode = "404", description = "Patient not found")
    @ApiResponse(responseCode = "400", description = "Invalid input data")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<PatientModel> update(@PathVariable Long id, @RequestBody PatientModel patientModel)
    {
        return ResponseEntity.ok(patientService.update(id, patientModel));
    }

    @PostMapping
    @Operation(summary = "Save patient data", description = "Method for saving patient data.", deprecated = false)
    @ApiResponse(responseCode = "201", description = "Patient created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid CPF or data")
    @ApiResponse(responseCode = "409", description = "CPF already exists")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<PatientModel> create(@RequestBody PatientModel patientModel)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(patientService.create(patientModel));
    }

    @PostMapping("/{id}/medicines")
    @Operation(summary = "Create a new medicine for a patient", description = "Creates a new medicine record associated with a specific patient")
    @ApiResponse(responseCode = "201", description = "Medicine created successfully")
    @ApiResponse(responseCode = "404", description = "Patient not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<PatientModel> createMedicineByPatient(@PathVariable Long id, @RequestBody MedicineModel medicineModel)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(patientService.createMedicineByPatient(id, medicineModel));
    }

    @PostMapping("/{id}/monitoring")
    @Operation(summary = "Create a new monitoring for a patient", description = "Creates a new monitoring record associated with a specific patient")
    @ApiResponse(responseCode = "201", description = "Monitoring created successfully")
    @ApiResponse(responseCode = "404", description = "Patient not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<PatientModel> createMonitoringByPatient(@PathVariable Long id, @RequestBody MonitoringModel monitoringModel)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(patientService.createMonitoringByPatient(id, monitoringModel));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete patient data", description = "Method for delete patient data.", deprecated = false)
    @ApiResponse(responseCode = "204", description = "Patient deleted successfully")
    @ApiResponse(responseCode = "404", description = "Patient not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<Void> delete(@PathVariable Long id)
    {
        patientService.delete(id);

        return ResponseEntity.noContent().build();
    }
}