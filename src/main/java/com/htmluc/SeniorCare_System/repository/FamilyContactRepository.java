package com.htmluc.SeniorCare_System.repository;

import com.htmluc.SeniorCare_System.model.FamilyContactModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface FamilyContactRepository extends JpaRepository<FamilyContactModel, Long> {
   Optional<FamilyContactModel> findByPatient_Id(UUID patientId);
}