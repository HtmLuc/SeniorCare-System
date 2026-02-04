package com.htmluc.SeniorCare_System.repository;

import com.htmluc.SeniorCare_System.model.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<UserModel, Long>
{
    @Query("SELECT u FROM tb_user u WHERE u.function LIKE %:func%")
    Page<UserModel> findByFunctionCustom(@Param("func") String func, Pageable pageable);
}
