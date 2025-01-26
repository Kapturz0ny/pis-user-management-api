package com.example.user_management_api.dtos;

import java.util.List;

public record UserDTO(Long id, String email, String first_name, String last_name, Long pesel) {
    @Override
    public Long id() {
        return id;
    }

    @Override
    public String email() {
        return email;
    }

    @Override
    public String first_name() {
        return first_name;
    }

    @Override
    public String last_name() {
        return last_name;
    }

    @Override
    public Long pesel() {
        return pesel;
    }
}