package com.htmluc.SeniorCare_System.controller;

import com.htmluc.SeniorCare_System.model.FamilyContactModel;
import com.htmluc.SeniorCare_System.repository.FamilyContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/v1/familyContact")
@Tag(name = "FamilyContact", description = "Endpoints for managing familyContact data")
public class FamilyContactController
{
    @Autowired
    private FamilyContactRepository familyContactRepository;

    @GetMapping
    @Operation(summary = "List all family contact", description = "Retrieves a comprehensive list of all registered family contact from the database.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of family contact")
    @ApiResponse(responseCode = "204", description = "No family contact found in the database")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<Page<FamilyContactModel>> listAll(Pageable pageable)
    {
        Page<FamilyContactModel> contacts = familyContactRepository.findAll(pageable);

        if (contacts.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(contacts);
    }

    @GetMapping("/search")
    @Operation(summary = "Filter family contacts", description = "Filter family contacts by city or relationship")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of family contact")
    @ApiResponse(responseCode = "204", description = "No family contact found in the database")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<Page<FamilyContactModel>> search(@RequestParam(required = false) String city, @RequestParam(required = false) String relationship, Pageable pageable)
    {
        Page<FamilyContactModel> contacts = familyContactRepository.findByCityOrRelationship(city, relationship, pageable);

        if (contacts.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(contacts);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get family contact by ID", description = "Retrieves a family contact by its unique identifier")
    @ApiResponse(responseCode = "200", description = "Family contact retrieved successfully")
    @ApiResponse(responseCode = "404", description = "Family contact not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<FamilyContactModel> getById(@PathVariable Long id)
    {
        return this.familyContactRepository.findById(id).map(familyContact -> ResponseEntity.ok(familyContact)).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update family contact by ID", description = "Updates an existing family contact with the provided data")
    @ApiResponse(responseCode = "200", description = "Family contact updated successfully")
    @ApiResponse(responseCode = "404", description = "Family contact not found")
    @ApiResponse(responseCode = "400", description = "Invalid input data")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<FamilyContactModel> updateById(@PathVariable Long id, @RequestBody FamilyContactModel familyContactModel)
    {
        return this.familyContactRepository.findById(id).map(info -> {
            info.setCep(familyContactModel.getCep());
            info.setUf(familyContactModel.getUf());
            info.setCity(familyContactModel.getCity());
            info.setNeighborhood(familyContactModel.getNeighborhood());
            info.setHouseNumber(familyContactModel.getHouseNumber());
            info.setRoad(familyContactModel.getRoad());
            info.setRelationship(familyContactModel.getRelationship());

            FamilyContactModel familyContact = this.familyContactRepository.save(info);
            return ResponseEntity.ok(familyContact);
        }).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create a new family contact", description = "Creates a new family contact with the provided data")
    @ApiResponse(responseCode = "201", description = "Family contact created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input data")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<FamilyContactModel> create(@RequestBody FamilyContactModel familyContactModel)
    {
        FamilyContactModel savedContact = this.familyContactRepository.save(familyContactModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedContact);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete family contact data by ID", description = "Method for delete family contact data")
    @ApiResponse(responseCode = "204", description = "Family contact deleted successfully")
    @ApiResponse(responseCode = "404", description = "Family contact not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<FamilyContactModel> delete(@PathVariable Long id)
    {
        if (!this.familyContactRepository.existsById(id))
        {
            return ResponseEntity.notFound().build();
        }
        this.familyContactRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
