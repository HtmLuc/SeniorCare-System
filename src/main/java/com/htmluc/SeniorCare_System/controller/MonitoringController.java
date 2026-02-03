package com.htmluc.SeniorCare_System.controller;

import com.htmluc.SeniorCare_System.model.MonitoringModel;
import com.htmluc.SeniorCare_System.repository.MonitoringRepository;
import com.htmluc.SeniorCare_System.repository.PatientRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/monitoring")
public class MonitoringController
{
    @Autowired
    private MonitoringRepository monitoringRepository;

    @Autowired
    private PatientRepository patientRepository;

    @GetMapping
    @Operation(summary = "List all monitoring", description = "Retrieves a comprehensive list of all registered monitoring from the database.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of monitoring")
    @ApiResponse(responseCode = "204", description = "No monitoring found in the database")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<List<MonitoringModel>> listAll()
    {
        List<MonitoringModel> allMonitoring = this.monitoringRepository.findAll();

        if (allMonitoring.isEmpty())
        {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(allMonitoring);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get monitoring by ID", description = "Retrieves a monitoring by its unique identifier")
    @ApiResponse(responseCode = "200", description = "Monitoring retrieved successfully")
    @ApiResponse(responseCode = "404", description = "Monitoring not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<MonitoringModel> getById(@PathVariable Long id)
    {
        return this.monitoringRepository.findById(id).map(monitoring -> ResponseEntity.ok(monitoring)).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update monitoring by ID", description = "Updates an existing monitoring with the provided data")
    @ApiResponse(responseCode = "200", description = "Monitoring updated successfully")
    @ApiResponse(responseCode = "404", description = "Monitoring not found")
    @ApiResponse(responseCode = "400", description = "Invalid input data")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<MonitoringModel> updateById(@PathVariable Long id, @RequestBody MonitoringModel monitoringModel)
    {
        return this.monitoringRepository.findById(id).map(info -> {
            info.setName(monitoringModel.getName());
            info.setBloodSugar(monitoringModel.getBloodSugar());
            info.setBloodPressure(monitoringModel.getBloodPressure());
            info.setHeartRate(monitoringModel.getHeartRate());
            info.setRespiratoryRate(monitoringModel.getRespiratoryRate());
            info.setSaturation(monitoringModel.getSaturation());
            info.setTemperature(monitoringModel.getTemperature());
            info.setTimeMeasure(monitoringModel.getTimeMeasure());

            MonitoringModel monitoring = this.monitoringRepository.save(info);
            return ResponseEntity.ok(monitoring);
        }).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/patient/{id}")
    @Operation(summary = "Create a new monitoring for a patient", description = "Creates a new monitoring record associated with a specific patient")
    @ApiResponse(responseCode = "201", description = "Monitoring created successfully")
    @ApiResponse(responseCode = "404", description = "Patient not found")
    @ApiResponse(responseCode = "400", description = "Invalid input data")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<MonitoringModel> create(@PathVariable Long id, @RequestBody MonitoringModel monitoringModel)
    {
        return this.patientRepository.findById(id).map(patient -> {
            monitoringModel.setPatient(patient);
            MonitoringModel monitoring = this.monitoringRepository.save(monitoringModel);
            return ResponseEntity.status(HttpStatus.CREATED).body(monitoring);
        }).orElse(ResponseEntity.badRequest().build());
    }
}
