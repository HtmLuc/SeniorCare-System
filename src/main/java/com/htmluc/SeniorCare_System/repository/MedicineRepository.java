package com.htmluc.SeniorCare_System.repository;

import com.htmluc.SeniorCare_System.model.MedicineModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MedicineRepository extends JpaRepository<MedicineModel, Long>
{
    @Query("SELECT m FROM tb_medicine m WHERE m.name LIKE %:name% AND m.acquisition = :acq")
    Page<MedicineModel> searchByNameAndAcquisition(@Param("name") String name, @Param("acq") String acq, Pageable pageable);
}
