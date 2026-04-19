package com.htmluc.SeniorCare_System.controller;

import com.htmluc.SeniorCare_System.model.FamilyContactModel;
import com.htmluc.SeniorCare_System.service.FamilyContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/familyContact")
@Tag(name = "FamilyContact", description = "Endpoints for managing familyContact data")
public class FamilyContactController
{
    private final FamilyContactService familyContactService;

    @GetMapping
    @Operation(summary = "List all family contact", description = "Retrieves a comprehensive list of all registered family contact from the database.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of family contact")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<Page<FamilyContactModel>> listAll(Pageable pageable)
    {
        return ResponseEntity.ok(familyContactService.listAll(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get family contact by ID", description = "Retrieves a family contact by its unique identifier")
    @ApiResponse(responseCode = "200", description = "Family contact retrieved successfully")
    @ApiResponse(responseCode = "404", description = "Family contact not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<FamilyContactModel> getById(@PathVariable Long id)
    {
        return ResponseEntity.ok(familyContactService.findById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update family contact by ID", description = "Updates an existing family contact with the provided data")
    @ApiResponse(responseCode = "200", description = "Family contact updated successfully")
    @ApiResponse(responseCode = "404", description = "Family contact not found")
    @ApiResponse(responseCode = "400", description = "Invalid input data")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<FamilyContactModel> update(@PathVariable Long id, @RequestBody FamilyContactModel familyContactModel)
    {
        return ResponseEntity.ok(familyContactService.update(id, familyContactModel));
    }

    @PostMapping
    @Operation(summary = "Create a new family contact", description = "Creates a new family contact with the provided data")
    @ApiResponse(responseCode = "201", description = "Family contact created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid CPF or data")
    @ApiResponse(responseCode = "409", description = "CPF already exists")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<FamilyContactModel> create(@RequestBody FamilyContactModel familyContactModel)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(familyContactService.create(familyContactModel));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete family contact data by ID", description = "Method for delete family contact data")
    @ApiResponse(responseCode = "204", description = "Family contact deleted successfully")
    @ApiResponse(responseCode = "404", description = "Family contact not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<Void> delete(@PathVariable Long id)
    {
        familyContactService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
