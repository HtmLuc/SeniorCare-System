package com.htmluc.SeniorCare_System.controller;

import com.htmluc.SeniorCare_System.model.MedicineModel;
import com.htmluc.SeniorCare_System.model.MonitoringModel;
import com.htmluc.SeniorCare_System.repository.MedicineRepository;
import com.htmluc.SeniorCare_System.repository.PatientRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/medicines")
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

    @PutMapping("/{id}")
    @Operation(summary = "Update medicine by ID", description = "Updates an existing medicine with the provided data")
    @ApiResponse(responseCode = "200", description = "Medicine updated successfully")
    @ApiResponse(responseCode = "404", description = "Medicine not found")
    @ApiResponse(responseCode = "400", description = "Invalid input data")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<MedicineModel> updateById(@PathVariable Long id, @RequestBody MedicineModel medicineModel)
    {
        return this.medicineRepository.findById(id).map(info -> {
            info.setName(medicineModel.getName());
            info.setAcquisition(medicineModel.getAcquisition());
            info.setDosage(medicineModel.getDosage());
            info.setFrequency(medicineModel.getFrequency());

            MedicineModel medicine = this.medicineRepository.save(info);
            return ResponseEntity.ok(medicine);
        }).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create a new medicine for a patient", description = "Creates a new medicine record associated with a specific patient")
    @ApiResponse(responseCode = "201", description = "Medicine created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input data")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<MedicineModel> create(@RequestBody MedicineModel medicineModel)
    {
        MedicineModel medicine = this.medicineRepository.save(medicineModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(medicine);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete medicine data", description = "Method for delete medicine data.", deprecated = false)
    @ApiResponse(responseCode = "204", description = "Medicine deleted successfully")
    @ApiResponse(responseCode = "404", description = "Medicine not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<Void> delete(@PathVariable Long id)
    {
        if (!this.medicineRepository.existsById(id))
        {
            return ResponseEntity.notFound().build();
        }
        this.medicineRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
