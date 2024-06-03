package com.aluracursos.literalura.repository;

import com.aluracursos.literalura.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByTitleContainingIgnoreCase(String bookTitle);

    List<Book> findByLanguageIgnoreCase(String language);

    @Query("SELECT b FROM Book b JOIN b.author a ORDER BY b.numberOfDownloads DESC LIMIT 10")
    List<Book> listTenPopularBooks();

    @Query("SELECT b FROM Book b JOIN b.author a WHERE a.name ILIKE %:authorName%")
    List<Book> listBooksByAnAuthor(String authorName);

}
