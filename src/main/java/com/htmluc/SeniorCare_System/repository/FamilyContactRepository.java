package com.htmluc.SeniorCare_System.repository;

import com.htmluc.SeniorCare_System.model.FamilyContactModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface FamilyContactRepository extends JpaRepository<FamilyContactModel, Long>
{
    Optional<FamilyContactModel> findByPatientId(Long id);
    @Query("SELECT f FROM tb_family_contact f WHERE f.city = :city OR f.relationship = :rel")
    Page<FamilyContactModel> findByCityOrRelationship(@Param("city") String city, @Param("rel") String rel, Pageable pageable);
}