package com.htmluc.SeniorCare_System.controller;

import com.htmluc.SeniorCare_System.model.UserModel;
import com.htmluc.SeniorCare_System.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/user")
@Tag(name = "User", description = "User Controller for managing endpoints for creation, retrieval, and updates user data.")
public class UserController
{
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    @Operation(summary = "List all users", description = "Retrieves a comprehensive list of all registered users from the database.", deprecated = false)
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of users")
    @ApiResponse(responseCode = "400", description = "Successfully retrieved the list of users")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<List<UserModel>> listAlll()
    {
        try
        {
            List<UserModel> users = this.userRepository.findAll();
            return ResponseEntity.ok(users);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping
    @Operation(summary = "Save user data", description = "Method for saving user data.", deprecated = false)
    @ApiResponse(responseCode = "201", description = "User created successfully")
    @ApiResponse(responseCode = "400", description = "User already exists")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<UserModel> create(@RequestBody @Valid UserModel userModel)
    {
        try
        {
            if(userModel.getId() != null && this.userRepository.existsById(userModel.getId()))
            {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
            var user = this.userRepository.save(userModel);

            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
}
