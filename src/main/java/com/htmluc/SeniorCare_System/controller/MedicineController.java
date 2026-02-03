package com.htmluc.SeniorCare_System.controller;

import com.htmluc.SeniorCare_System.model.MedicineModel;
import com.htmluc.SeniorCare_System.repository.MedicineRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping
public class MedicineController
{
    @Autowired
    private MedicineRepository medicineRepository;

    @GetMapping
    @Operation(summary = "List all medicines", description = "Retrieves a comprehensive list of all registered medicines from the database.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of medicine")
    @ApiResponse(responseCode = "204", description = "No medicine found in the database")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<List<MedicineModel>> listAll()
    {
        List<MedicineModel> allMedicine = this.medicineRepository.findAll();

        if (allMedicine.isEmpty())
        {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(allMedicine);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get medicine by ID", description = "Retrieves a medicine by its unique identifier")
    @ApiResponse(responseCode = "200", description = "Medicine retrieved successfully")
    @ApiResponse(responseCode = "404", description = "Medicine not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<MedicineModel> getById(@PathVariable Long id)
    {
        return this.medicineRepository.findById(id).map(medicine -> ResponseEntity.ok(medicine)).orElse(ResponseEntity.notFound().build());
    }
}
