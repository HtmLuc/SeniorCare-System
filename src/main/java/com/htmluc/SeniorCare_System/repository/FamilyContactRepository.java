package com.htmluc.SeniorCare_System.repository;

import com.htmluc.SeniorCare_System.model.FamilyContactModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FamilyContactRepository extends JpaRepository<FamilyContactModel, Long>
{
    Optional<FamilyContactModel> findByPersonId(Long id);
}