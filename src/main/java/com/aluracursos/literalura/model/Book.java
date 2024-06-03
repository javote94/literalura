package com.aluracursos.literalura.model;

import com.aluracursos.literalura.dtos.BookDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String title;

    private String language;

    private Integer numberOfDownloads;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    public Book(BookDTO bookDTO, Author author) {
        this.title = bookDTO.title();
        this.language = bookDTO.getFirstLanguage();
        this.numberOfDownloads = bookDTO.numberOfDownloads();
        this.author = author;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", language='" + language + '\'' +
                ", numberOfDownloads=" + numberOfDownloads +
                ", author=" + author.getName() +
                '}';
    }
}

/*
Nota(1):
Con CascadeType.PERSIST: Al persistir el Book, Hibernate automáticamente persistirá
el Author asociado si aún no está persistido.
 */