package com.htmluc.SeniorCare_System.controller;

import com.htmluc.SeniorCare_System.model.UserModel;
import com.htmluc.SeniorCare_System.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users")
@Tag(name = "User", description = "Endpoints for managing user data")
public class UserController
{
    private UserService userService;

    @GetMapping
    @Operation(summary = "List all users", description = "Retrieves a comprehensive list of all registered users from the database.", deprecated = false)
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of users")
    @ApiResponse(responseCode = "204", description = "No user found in the database")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<Page<UserModel>> listAll(Pageable pageable)
    {
        return ResponseEntity.ok(userService.listAll(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID", description = "Retrieves a user by its unique identifier")
    @ApiResponse(responseCode = "200", description = "User retrieved successfully")
    @ApiResponse(responseCode = "404", description = "User not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<UserModel> getById(@PathVariable Long id)
    {
        return ResponseEntity.ok(userService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Save user data", description = "Method for saving user data.", deprecated = false)
    @ApiResponse(responseCode = "201", description = "User created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid CPF or data")
    @ApiResponse(responseCode = "409", description = "CPF already exists")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<UserModel> create(@RequestBody @Valid UserModel userModel)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(userModel));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user data", description = "Method for delete user data.", deprecated = false)
    @ApiResponse(responseCode = "204", description = "User deleted successfully")
    @ApiResponse(responseCode = "404", description = "User not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<Void> delete(@PathVariable Long id)
    {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
