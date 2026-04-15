package com.htmluc.SeniorCare_System.repository;

import com.htmluc.SeniorCare_System.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserModel, Long>
{
}
