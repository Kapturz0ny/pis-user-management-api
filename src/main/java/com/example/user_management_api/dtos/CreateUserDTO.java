package com.example.user_management_api.dtos;

public record CreateUserDTO(String email, String first_name, String last_name, Long pesel) {}

