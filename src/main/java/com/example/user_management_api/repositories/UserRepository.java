package com.example.user_management_api.repositories;

import com.example.user_management_api.entities.UserManagement;
import com.example.user_management_api.entities.UserManagement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserRepository extends JpaRepository<UserManagement, Long>, JpaSpecificationExecutor<UserManagement> {}

