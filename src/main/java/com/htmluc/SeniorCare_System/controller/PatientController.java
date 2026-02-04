package com.htmluc.SeniorCare_System.controller;

import com.htmluc.SeniorCare_System.model.PatientModel;
import com.htmluc.SeniorCare_System.model.PersonModel;
import com.htmluc.SeniorCare_System.repository.PatientRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/v1/patients")
@Tag(name = "Patient", description = "Endpoints for managing patient data")
public class PatientController
{
    @Autowired
    private PatientRepository patientRepository;

    // ========== MÉTODO NOVO: PÁGINA HTML DO FORMULÁRIO ==========
    @GetMapping("/form")
    public String patientForm(Model model, 
                             @RequestParam(required = false) Long id,
                             Authentication authentication) {
        
        System.out.println("===== ACESSANDO FORMULÁRIO DE PACIENTE =====");
        System.out.println("ID: " + id);
        System.out.println("Usuário: " + (authentication != null ? authentication.getName() : "null"));
        
        // Informações do usuário logado
        if (authentication != null) {
            String username = authentication.getName();
            String role = authentication.getAuthorities()
                .iterator().next().getAuthority()
                .replace("ROLE_", "");
            
            System.out.println("Username: " + username);
            System.out.println("Role: " + role);
            
            model.addAttribute("username", username);
            model.addAttribute("role", role);
        } else {
            model.addAttribute("username", "Usuário");
            model.addAttribute("role", "ADMIN");
        }
        
        // Determinar modo (novo ou editar)
        if (id != null && id > 0) {
            System.out.println("MODO: EDITAR paciente ID: " + id);
            model.addAttribute("modo", "editar");
            
            // Tentar carregar paciente existente
            try {
                Optional<PatientModel> optional = patientRepository.findById(id);
                if (optional.isPresent()) {
                    PatientModel paciente = optional.get();
                    System.out.println("Paciente encontrado: " + 
                        (paciente.getPerson() != null ? paciente.getPerson().getName() : "Sem nome"));
                    model.addAttribute("paciente", paciente);
                } else {
                    System.out.println("Paciente NÃO encontrado, criando novo");
                    PatientModel paciente = new PatientModel();
                    paciente.setPerson(new PersonModel());
                    model.addAttribute("paciente", paciente);
                }
            } catch (Exception e) {
                System.out.println("Erro ao buscar paciente: " + e.getMessage());
                PatientModel paciente = new PatientModel();
                paciente.setPerson(new PersonModel());
                model.addAttribute("paciente", paciente);
            }
        } else {
            System.out.println("MODO: NOVO paciente");
            model.addAttribute("modo", "novo");
            
            // Criar paciente vazio
            PatientModel paciente = new PatientModel();
            paciente.setPerson(new PersonModel());
            model.addAttribute("paciente", paciente);
        }
        
        System.out.println("===== RETORNANDO PÁGINA paciente-form.html =====");
        return "paciente-form";
    }

    // ========== MÉTODOS EXISTENTES (API REST) ==========
    
    @GetMapping
    @ResponseBody  // ← ADICIONAR para retornar JSON
    @Operation(summary = "List all patients", description = "Retrieves a comprehensive list of all registered patients from the database.", deprecated = false)
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of patient")
    @ApiResponse(responseCode = "204", description = "No patients found in the database")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<List<PatientModel>> listAll()
    {
        List<PatientModel> allPatients = this.patientRepository.findAll();
        if (allPatients.isEmpty())
        {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(allPatients);
    }

    @GetMapping("/{id}")
    @ResponseBody  // ← ADICIONAR para retornar JSON
    @Operation(summary = "Get patient by ID", description = "Retrieves a patient by its unique identifier")
    @ApiResponse(responseCode = "200", description = "Patient retrieved successfully")
    @ApiResponse(responseCode = "404", description = "patient not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<PatientModel> getById(@PathVariable Long id)
    {
        return this.patientRepository.findById(id)
                .map(patient -> ResponseEntity.ok(patient))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @ResponseBody  // ← ADICIONAR para retornar JSON
    @Operation(summary = "Update patient by ID", description = "Updates an existing patient with the provided data")
    @ApiResponse(responseCode = "200", description = "Patient updated successfully")
    @ApiResponse(responseCode = "404", description = "Patient not found")
    @ApiResponse(responseCode = "400", description = "Invalid input data")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<PatientModel> updateById(@PathVariable Long id, @RequestBody PatientModel patientModel)
    {
        return this.patientRepository.findById(id).map(info -> {
            info.setGender(patientModel.getGender());
            info.setDegree_dependence(patientModel.getDegree_dependence());
            info.setObservations(patientModel.getObservations());

            PatientModel patient = this.patientRepository.save(info);
            return ResponseEntity.ok(patient);
        }).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseBody  // ← ADICIONAR para retornar JSON
    @Operation(summary = "Save patient data", description = "Method for saving patient data.", deprecated = false)
    @ApiResponse(responseCode = "201", description = "Patient created successfully")
    @ApiResponse(responseCode = "409", description = "Patient already exists")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<PatientModel> create(@RequestBody PatientModel patientModel)
    {
        if (patientRepository.existsByPersonCpf(patientModel.getPerson().getCpf()))
        {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        patientModel.setId(null);
        PatientModel patient = patientRepository.save(patientModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(patient);
    }

    @DeleteMapping("{id}")
    @ResponseBody  // ← ADICIONAR para retornar JSON
    @Operation(summary = "Delete patient data", description = "Method for delete patient data.", deprecated = false)
    @ApiResponse(responseCode = "204", description = "Patient deleted successfully")
    @ApiResponse(responseCode = "404", description = "Patient not found")
    public ResponseEntity<Void> delete(@PathVariable Long id)
    {
        if (!this.patientRepository.existsById(id))
        {
            return ResponseEntity.notFound().build();
        }
        this.patientRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}