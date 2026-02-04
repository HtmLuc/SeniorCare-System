package com.htmluc.SeniorCare_System.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Controller
public class WebController {
    
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
    
    @GetMapping("/")
    public String home() {
        return "redirect:/login";
    }
    
    @GetMapping("/public/info")
    @ResponseBody 
    public String publicInfo() {
        return "Sistema SeniorCare está funcionando!\n" +
               "Autenticação: Spring Security\n" +
               "Banco: H2 Database\n" +
               "API: Swagger UI disponível";
    }
    
    @GetMapping("/acesso-negado")
    public String acessoNegado(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            model.addAttribute("username", auth.getName());
            model.addAttribute("role", auth.getAuthorities().iterator().next().getAuthority());
        }
        return "acesso-negado";
    }
}