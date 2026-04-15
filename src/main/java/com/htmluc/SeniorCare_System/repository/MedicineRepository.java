package com.htmluc.SeniorCare_System.repository;

import com.htmluc.SeniorCare_System.model.MedicineModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicineRepository extends JpaRepository<MedicineModel, Long>
{
}
