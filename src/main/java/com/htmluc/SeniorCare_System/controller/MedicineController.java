package com.htmluc.SeniorCare_System.controller;

import com.htmluc.SeniorCare_System.model.MedicineModel;
import com.htmluc.SeniorCare_System.service.MedicineService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/medicines")
public class MedicineController
{
    private final MedicineService medicineService;

    @GetMapping
    @Operation(summary = "List all medicines", description = "Retrieves a comprehensive list of all registered medicines from the database.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of medicine")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<Page<MedicineModel>> listAll(Pageable pageable)
    {
        return ResponseEntity.ok(medicineService.listAll(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get medicine by ID", description = "Retrieves a medicine by its unique identifier")
    @ApiResponse(responseCode = "200", description = "Medicine retrieved successfully")
    @ApiResponse(responseCode = "404", description = "Medicine not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<MedicineModel> getById(@PathVariable Long id)
    {
        return ResponseEntity.ok(medicineService.findById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update medicine by ID", description = "Updates an existing medicine with the provided data")
    @ApiResponse(responseCode = "200", description = "Medicine updated successfully")
    @ApiResponse(responseCode = "404", description = "Medicine not found")
    @ApiResponse(responseCode = "400", description = "Invalid input data")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<MedicineModel> update(@PathVariable Long id, @RequestBody MedicineModel medicineModel)
    {
        return ResponseEntity.ok(medicineService.update(id, medicineModel));

    }

    @PostMapping
    @Operation(summary = "Create a new medicine", description = "Creates a new medicine record associated with a specific patient")
    @ApiResponse(responseCode = "201", description = "Medicine created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input data")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<MedicineModel> create(@RequestBody MedicineModel medicineModel)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(medicineService.create(medicineModel));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete medicine data", description = "Method for delete medicine data.", deprecated = false)
    @ApiResponse(responseCode = "204", description = "Medicine deleted successfully")
    @ApiResponse(responseCode = "404", description = "Medicine not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<Void> delete(@PathVariable Long id)
    {
        medicineService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
