package com.example.crudejemplo.control;
import com.example.crudejemplo.entity.Libro;
import com.example.crudejemplo.service.LibrosServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/Libro")
public class BookController
{
    @Autowired
    private  LibrosServices librosServices;
    @GetMapping
    public List<Libro> getAll(){
        return librosServices.getLibros();
    }
    @PostMapping
    public void saveupdate(@RequestBody Libro libro){
        librosServices.save_or_Update(libro);
    }

    @DeleteMapping("/{LibroId}")
    public void saveupdate(@PathVariable("LibroId") Long LibroId){
        librosServices.delete(LibroId);
    }
    @GetMapping("/{LibroId}")
    public Optional<Libro> getbyId(@PathVariable("/{LibroId}") Long LibroId ){
         return librosServices.getLibro(LibroId);
    }
}
