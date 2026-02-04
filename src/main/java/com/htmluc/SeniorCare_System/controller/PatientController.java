package com.htmluc.SeniorCare_System.controller;

import com.htmluc.SeniorCare_System.model.MedicineModel;
import com.htmluc.SeniorCare_System.model.MonitoringModel;
import com.htmluc.SeniorCare_System.model.PatientModel;
import com.htmluc.SeniorCare_System.repository.MedicineRepository;
import com.htmluc.SeniorCare_System.repository.MonitoringRepository;
import com.htmluc.SeniorCare_System.model.PersonModel;
import com.htmluc.SeniorCare_System.repository.PatientRepository;
import com.htmluc.SeniorCare_System.service.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/v1/patients")
@Tag(name = "Patient", description = "Endpoints for managing patient data")
public class PatientController
{
    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private MedicineRepository medicineRepository;

    @Autowired
    private MonitoringRepository monitoringRepository;

    @Autowired
    private PersonService personService;

    @GetMapping("/form")
    public String patientForm(Model model, @RequestParam(required = false) Long id, Authentication authentication)
    {
        
        System.out.println("ACESSANDO FORMULÁRIO DE PACIENTE");
        System.out.println("ID: " + id);
        System.out.println("Usuário: " + (authentication != null ? authentication.getName() : "null"));
        
        if (authentication != null)
        {
            String username = authentication.getName();
            String role = authentication.getAuthorities()
                .iterator().next().getAuthority()
                .replace("ROLE_", "");
            
            System.out.println("Username: " + username);
            System.out.println("Role: " + role);
            
            model.addAttribute("username", username);
            model.addAttribute("role", role);
        }
        else
        {
            model.addAttribute("username", "Usuário");
            model.addAttribute("role", "ADMIN");
        }
      
        if (id != null && id > 0) {
            System.out.println("MODO: EDITAR paciente ID: " + id);
            model.addAttribute("modo", "editar");
            try
            {
                Optional<PatientModel> optional = patientRepository.findById(id);
                if (optional.isPresent())
                {
                    PatientModel paciente = optional.get();
                    System.out.println("Paciente encontrado: " + 
                        (paciente.getPerson() != null ? paciente.getPerson().getName() : "Sem nome"));
                    model.addAttribute("paciente", paciente);
                }
                else
                {
                    System.out.println("Paciente NÃO encontrado, criando novo");
                    PatientModel paciente = new PatientModel();
                    paciente.setPerson(new PersonModel());
                    model.addAttribute("paciente", paciente);
                }
            }
            catch (Exception e)
            {
                System.out.println("Erro ao buscar paciente: " + e.getMessage());
                PatientModel paciente = new PatientModel();
                paciente.setPerson(new PersonModel());
                model.addAttribute("paciente", paciente);
            }
        }
        else
        {
            System.out.println("MODO: NOVO paciente");
            model.addAttribute("modo", "novo");
            
            PatientModel paciente = new PatientModel();
            paciente.setPerson(new PersonModel());
            model.addAttribute("paciente", paciente);
        }
        
        System.out.println("RETORNANDO PÁGINA paciente-form.html");
        return "paciente-form";
    }
  
    @GetMapping
    @ResponseBody
    @Operation(summary = "List all patients", description = "Retrieves a comprehensive list of all registered patients from the database.", deprecated = false)
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of patient")
    @ApiResponse(responseCode = "204", description = "No patients found in the database")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<Page<PatientModel>> listAll(Pageable pageable)
    {
        Page<PatientModel> patients = patientRepository.findAll(pageable);

        if (patients.isEmpty())
        {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(patients);
    }

    @GetMapping("/search")
    @Operation(summary = "Filter patients by dependence and nationality", description = "Filters patients using business rules with pagination")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of patient")
    @ApiResponse(responseCode = "204", description = "No patients found in the database")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<Page<PatientModel>> filterPatients(@RequestParam int degree, @RequestParam String nationality, Pageable pageable)
    {
        Page<PatientModel> patients = patientRepository.filterByDependenceAndNat(degree, nationality, pageable);

        if (patients.isEmpty())
        {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(patients);
    }


    @GetMapping("/{id}")
    @ResponseBody
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
    @ResponseBody
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
            personService.updatePerson(id, patientModel.getPerson());

            PatientModel patient = this.patientRepository.save(info);
            return ResponseEntity.ok(patient);
        }).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseBody
    @Operation(summary = "Save patient data", description = "Method for saving patient data.", deprecated = false)
    @ApiResponse(responseCode = "201", description = "Patient created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid CPF or data")
    @ApiResponse(responseCode = "409", description = "CPF already exists")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<PatientModel> create(@RequestBody PatientModel patientModel)
    {
        var savedPerson = personService.createPerson(patientModel.getPerson());

        patientModel.setPerson(savedPerson);
        patientModel.setId(savedPerson.getId());
        PatientModel patient = patientRepository.save(patientModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(patient);
    }

    @PostMapping("/{id}/medicines")
    @Operation(summary = "Create a new medicine for a patient", description = "Creates a new medicine record associated with a specific patient")
    @ApiResponse(responseCode = "201", description = "Medicine created successfully")
    @ApiResponse(responseCode = "404", description = "Patient not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<MedicineModel> createByPatient(@PathVariable Long id, @RequestBody MedicineModel medicineModel)
    {
        return patientRepository.findById(id).map(patient -> {
            MedicineModel medicine = medicineRepository.save(medicineModel);
          
            if (patient.getMedicines() == null)
            {
                patient.setMedicines(new java.util.ArrayList<>());
            }

            patient.getMedicines().add(medicine);
            patientRepository.save(patient);
            return ResponseEntity.status(HttpStatus.CREATED).body(medicine);
        }).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/monitoring")
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
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    @Operation(summary = "Delete patient data", description = "Method for delete patient data.", deprecated = false)
    @ApiResponse(responseCode = "204", description = "Patient deleted successfully")
    @ApiResponse(responseCode = "404", description = "Patient not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
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