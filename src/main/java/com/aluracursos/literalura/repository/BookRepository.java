package com.aluracursos.literalura.repository;

import com.aluracursos.literalura.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByTitle(String bookTitle);

    List<Book> findByTitleContainingIgnoreCase(String bookTitle);

}
