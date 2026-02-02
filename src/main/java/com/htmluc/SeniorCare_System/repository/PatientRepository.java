package com.htmluc.SeniorCare_System.repository;

import com.htmluc.SeniorCare_System.model.PatientModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<PatientModel, Long>
{
    boolean existsByPersonCpf(String cpf);
}
