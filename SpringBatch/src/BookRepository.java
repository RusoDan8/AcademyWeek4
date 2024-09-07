package com.javatechie.spring.batch.repository;

import com.javatechie.spring.batch.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {
}