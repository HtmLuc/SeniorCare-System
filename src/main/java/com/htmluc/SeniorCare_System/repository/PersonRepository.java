package com.htmluc.SeniorCare_System.repository;

import com.htmluc.SeniorCare_System.model.PersonModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PersonRepository extends JpaRepository<PersonModel, Long>
{
    @Query("SELECT p FROM tb_person p WHERE p.cpf = :cpf OR p.name LIKE %:name%")
    Page<PersonModel> findByCpfOrName(@Param("cpf") String cpf, @Param("name") String name, Pageable pageable);
}
