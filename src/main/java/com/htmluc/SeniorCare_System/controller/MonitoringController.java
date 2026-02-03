package com.htmluc.SeniorCare_System.controller;

import com.htmluc.SeniorCare_System.model.MonitoringModel;
import com.htmluc.SeniorCare_System.repository.MonitoringRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/monitoring")
public class MonitoringController
{
    @Autowired
    private MonitoringRepository monitoringRepository;


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
}
