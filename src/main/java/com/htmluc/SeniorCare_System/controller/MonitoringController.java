package com.htmluc.SeniorCare_System.controller;

import com.htmluc.SeniorCare_System.model.MonitoringModel;
import com.htmluc.SeniorCare_System.service.MonitoringService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/monitoring")
public class MonitoringController
{
    private final MonitoringService monitoringService;

    @GetMapping
    @Operation(summary = "List all monitoring", description = "Retrieves a comprehensive list of all registered monitoring from the database.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of monitoring")
    @ApiResponse(responseCode = "204", description = "No monitoring found in the database")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<Page<MonitoringModel>> listAll(Pageable pageable)
    {
        Page<MonitoringModel> monitorings = monitoringService.listAll(pageable);

        if (monitorings.isEmpty())
        {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(monitorings);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get monitoring by ID", description = "Retrieves a monitoring by its unique identifier")
    @ApiResponse(responseCode = "200", description = "Monitoring retrieved successfully")
    @ApiResponse(responseCode = "404", description = "Monitoring not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<MonitoringModel> getById(@PathVariable Long id)
    {
        return this.monitoringService.findById(id).map(monitoring -> ResponseEntity.ok(monitoring)).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update monitoring by ID", description = "Updates an existing monitoring with the provided data")
    @ApiResponse(responseCode = "200", description = "Monitoring updated successfully")
    @ApiResponse(responseCode = "404", description = "Monitoring not found")
    @ApiResponse(responseCode = "400", description = "Invalid input data")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<MonitoringModel> updateById(@PathVariable Long id, @RequestBody MonitoringModel monitoringModel)
    {
        return ResponseEntity.ok(monitoringService.update(id, monitoringModel));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete monitoring data by ID", description = "Method for delete monitoring data")
    @ApiResponse(responseCode = "204", description = "Monitoring deleted successfully")
    @ApiResponse(responseCode = "404", description = "Monitoring not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<Void> delete(@PathVariable Long id)
    {
        monitoringService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
