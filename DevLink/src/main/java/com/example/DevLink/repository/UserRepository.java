package com.example.DevLink.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.DevLink.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
