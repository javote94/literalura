package com.aluracursos.literalura.model;

import com.aluracursos.literalura.dtos.AuthorDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="authors")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer birthYear;

    private Integer deathYear;

    @OneToMany(mappedBy = "author")
    private List<Book> books;


    public Author (AuthorDTO authorDTO) {
        this.name = authorDTO.name();
        this.birthYear = authorDTO.birthYear();
        this.deathYear = authorDTO.deathYear();
        //no es necesario setear el atributo "books"
    }


    @Override
    public String toString() {
        return "Author{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", birthYear=" + birthYear +
                ", deathYear=" + deathYear +
                ", books=" + books.stream().map(Book::getTitle).collect(Collectors.joining(", ")) +
                '}';
    }
}
