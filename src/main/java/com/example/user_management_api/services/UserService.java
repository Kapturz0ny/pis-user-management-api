package com.example.user_management_api.services;

import com.example.user_management_api.dtos.*;
import com.example.user_management_api.entities.UserManagement;
import com.example.user_management_api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
//
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDTO> getAllUsers() {
        List<UserManagement> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No users found");
        }
        return users.stream()
                    .map(this::mapToUserDTO)
                    .collect(Collectors.toList());
    }

    public UserDTO getUserById(Long id) {
        UserManagement user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User with ID " + id + " not found"));
        return mapToUserDTO(user);
    }

    public UserDTO createUser(CreateUserDTO userDTO) {

        // Map DTO to entity
        UserManagement user = new UserManagement();
        user.setEmail(userDTO.email());
        user.setFirst_name(userDTO.first_name());
        user.setLast_name(userDTO.last_name());
        user.setPesel(userDTO.pesel());

        // Save and return
        return mapToUserDTO(userRepository.save(user));
    }

    public UserDTO updateUser(Long id, UserUpdateDTO userUpdateDTO) {
        UserManagement user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book with ID " + id + " not found"));

        // Map fields from DTO
        mapUpdate(user, userUpdateDTO);

        // Save and return
        return mapToUserDTO(userRepository.save(user));
    }

    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private void mapUpdate(UserManagement user, UserUpdateDTO userUpdateDTO) {

        if (userUpdateDTO.getEmail() != null) {
            user.setEmail(userUpdateDTO.getEmail());
        }

        if (userUpdateDTO.getFirst_name() != null) {
            user.setFirst_name(userUpdateDTO.getFirst_name());
        }

        if (userUpdateDTO.getLast_name() != null) {
            user.setLast_name(userUpdateDTO.getLast_name());
        }

        if (userUpdateDTO.getPesel() != null) {
            user.setPesel(userUpdateDTO.getPesel());
        }
    }

    private UserDTO mapToUserDTO(UserManagement user) {
        return new UserDTO(
                user.getId(),
                user.getEmail(),
                user.getFirst_name(),
                user.getLast_name(),
                user.getPesel()
        );
    }
}

