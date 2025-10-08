package com.example.library.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.library.entity.Book;
import com.example.library.entity.User;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByOwner(User owner);
}
