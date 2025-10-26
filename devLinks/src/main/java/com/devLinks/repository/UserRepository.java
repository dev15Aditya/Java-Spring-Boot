package com.devLinks.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devLinks.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
