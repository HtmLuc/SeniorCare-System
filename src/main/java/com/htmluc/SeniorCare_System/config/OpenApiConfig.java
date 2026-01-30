package com.htmluc.SeniorCare_System.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Senior Care System",
                version = "0.1",
                contact = @Contact(name = "Lucas Dantas & Liriel Felix", email = "lucas.medeiros.107@ufrn.edu.br"),
                summary = "Comprehensive health and clinical management system for elderly care homes.",
                description = "This API provides a complete suite of tools for managing nursing homes. " +
                        "It includes modules for real-time vital signs monitoring, medication scheduling, " +
                        "clinical procedure records (bandages), and management of patients, staff, and family contacts."
        )
)
public class OpenApiConfig
{
}
