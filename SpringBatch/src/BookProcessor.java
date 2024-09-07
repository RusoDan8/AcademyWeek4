package com.javatechie.spring.batch.config;

import com.javatechie.spring.batch.entity.Book;
import org.springframework.batch.item.ItemProcessor;

public class BookProcessor implements ItemProcessor<Book, Book> {

    @Override
    public Book process(Book book) throws Exception {
        // Puedes agregar cualquier lógica de filtrado o transformación aquí.
        // Por ejemplo, procesar solo libros que sean de un género específico:
        if ("Fiction".equals(book.getGenre())) {
            return book; // Procesar solo libros del género "Fiction"
        } else {
            return null; // Ignorar libros que no cumplan con el criterio
        }
    }
}
