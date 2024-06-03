package com.aluracursos.literalura.repository;

import com.aluracursos.literalura.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    Optional<Author> findByName(String name);

    //FETCH JOIN asegura que la colección libros se cargue junto con los autores en una única consulta.
    @Query("SELECT a FROM Author a LEFT JOIN FETCH a.books")
    List<Author> findAllWithBooks();

    @Query("SELECT a FROM Author a LEFT JOIN FETCH a.books WHERE a.birthYear <= :year AND (a.deathYear IS NULL OR a.deathYear >= :year)")
    List<Author> findAuthorsLivingInYear(@Param("year") int year);

}
