package com.example.user_management_api.controllers;

import com.example.user_management_api.dtos.CreateUserDTO;
import com.example.user_management_api.dtos.UserDTO;
import com.example.user_management_api.dtos.UserUpdateDTO;
import com.example.user_management_api.entities.UserManagement;
import com.example.user_management_api.kafka.KafkaProducer;
import com.example.user_management_api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final KafkaProducer kafkaProducer;

    @Autowired
    public UserController(UserService userService, KafkaProducer kafkaProducer) {
        this.userService = userService;
        this.kafkaProducer = kafkaProducer;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        UserDTO user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/")
    public ResponseEntity<UserDTO> createBook(@RequestBody CreateUserDTO createUserDTO) {
        UserDTO createdUser = userService.createUser(createUserDTO);
        kafkaProducer.sendMessage("POST", createdUser.id());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserUpdateDTO userUpdateDTO) {
        UserDTO updatedUser = userService.updateUser(id, userUpdateDTO);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        boolean ifDeleted = userService.deleteUser(id);
        if (ifDeleted){
            kafkaProducer.sendMessage("DELETE", id);
        }
        return ResponseEntity.noContent().build();
    }
}