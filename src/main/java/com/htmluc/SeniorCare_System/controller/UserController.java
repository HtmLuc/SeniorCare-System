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

import java.util.List;

@RestController
@RequestMapping("/v1/users")
@Tag(name = "User", description = "Endpoints for managing user data")
public class UserController
{
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    @Operation(summary = "List all users", description = "Retrieves a comprehensive list of all registered users from the database.", deprecated = false)
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of users")
    @ApiResponse(responseCode = "204", description = "No user found in the database")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<List<UserModel>> listAll()
    {
        List<UserModel> allUsers = this.userRepository.findAll();
        if (allUsers.isEmpty())
        {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(allUsers);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID", description = "Retrieves a user by its unique identifier")
    @ApiResponse(responseCode = "200", description = "User retrieved successfully")
    @ApiResponse(responseCode = "404", description = "User not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<UserModel> getById(@PathVariable Long id)
    {
        return userRepository.findById(id).map(user -> ResponseEntity.ok(user)).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Save user data", description = "Method for saving user data.", deprecated = false)
    @ApiResponse(responseCode = "201", description = "User created successfully")
    @ApiResponse(responseCode = "409", description = "User already exists")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<UserModel> create(@RequestBody @Valid UserModel userModel)
    {
        userModel.setId(null);
        UserModel user = this.userRepository.save(userModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user data", description = "Method for delete user data.", deprecated = false)
    @ApiResponse(responseCode = "204", description = "User deleted successfully")
    @ApiResponse(responseCode = "404", description = "User not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if(!this.userRepository.existsById(id))
        {
            return ResponseEntity.notFound().build();
        }
        this.userRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
