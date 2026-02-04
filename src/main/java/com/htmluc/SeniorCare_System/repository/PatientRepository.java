package com.htmluc.SeniorCare_System.repository;

import com.htmluc.SeniorCare_System.model.PatientModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PatientRepository extends JpaRepository<PatientModel, Long>
{
    boolean existsByPersonCpf(String cpf);
    @Query("SELECT p FROM tb_patient p WHERE p.degree_dependence = :degree AND p.nationality = :nat")
    Page<PatientModel> filterByDependenceAndNat(@Param("degree") int degree, @Param("nat") String nat, Pageable pageable);
}
