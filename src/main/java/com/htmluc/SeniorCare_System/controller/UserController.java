package com.htmluc.SeniorCare_System.controller;

import com.htmluc.SeniorCare_System.model.UserModel;
import com.htmluc.SeniorCare_System.repository.UserRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/user")
public class UserController
{
    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity create(@RequestBody @Valid UserModel userModel)
    {
        try
        {
            var user = this.userRepository.findById(userModel.getId());
            if(userModel.getId() != null && this.userRepository.existsById(userModel.getId()))
            {
                throw new IllegalArgumentException("User already exists");
            }
            this.userRepository.save(userModel);
            return ResponseEntity.ok().build();
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().build();
        }
    }
}
