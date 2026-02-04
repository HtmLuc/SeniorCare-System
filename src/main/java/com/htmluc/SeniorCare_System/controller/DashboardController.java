package com.htmluc.SeniorCare_System.controller;

import com.htmluc.SeniorCare_System.model.PatientModel;
import com.htmluc.SeniorCare_System.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class DashboardController {
    
    @Autowired
    private PatientRepository patientRepository;
    
    @GetMapping("/dashboard")
    public String dashboard(Model model, 
                          @RequestParam(required = false) String success,
                          @RequestParam(required = false) String error) {
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        String role = auth.getAuthorities().iterator().next().getAuthority();
        
        List<PatientModel> pacientes = patientRepository.findAll();
        
        model.addAttribute("username", username);
        model.addAttribute("role", role.replace("ROLE_", ""));
        model.addAttribute("pacientes", pacientes);
        model.addAttribute("totalPacientes", pacientes.size());
        
        if (success != null) {
            model.addAttribute("successMessage", getSuccessMessage(success));
        }
        if (error != null) {
            model.addAttribute("errorMessage", getErrorMessage(error));
        }
        
        return "dashboard";
    }
    
    private String getSuccessMessage(String type) {
        switch (type) {
            case "paciente_criado":
                return "Paciente criado com sucesso!";
            case "paciente_atualizado":
                return "Paciente atualizado com sucesso!";
            case "paciente_excluido":
                return "Paciente excluído com sucesso!";
            default:
                return "Operação realizada com sucesso!";
        }
    }
    
    private String getErrorMessage(String type) {
        switch (type) {
            case "paciente_existe":
                return "Erro: Paciente já existe!";
            case "paciente_nao_encontrado":
                return "Erro: Paciente não encontrado!";
            default:
                return "Ocorreu um erro na operação!";
        }
    }
}