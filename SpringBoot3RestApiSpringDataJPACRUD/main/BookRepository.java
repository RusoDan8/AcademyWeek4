package com.example.crudejemplo.repository;

import com.example.crudejemplo.entity.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Libro,Long> {

}
