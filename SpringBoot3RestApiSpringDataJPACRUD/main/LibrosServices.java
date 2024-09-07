package com.example.crudejemplo.service;
import com.example.crudejemplo.entity.Libro;
import com.example.crudejemplo.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LibrosServices {
    @Autowired
    BookRepository bookRepository;


    public List<Libro> getLibros(){
        return bookRepository.findAll();
    }
    public Optional<Libro> getLibro(Long id){
        return bookRepository.findById(id);
    }
    public void  save_or_Update(Libro libro){
        bookRepository.save(libro);
    }
    public void delete(Long id){
        bookRepository.deleteById(id);
    }

}
