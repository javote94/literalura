package com.aluracursos.literalura.service;

import com.aluracursos.literalura.dtos.AuthorInfoDTO;
import com.aluracursos.literalura.model.Author;
import com.aluracursos.literalura.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    public List<AuthorInfoDTO> listAllAuthors() {
        List<Author> authors = authorRepository.findAllWithBooks();
        return authors.stream()
                .map(a -> convertToInfoDTO(a))
                .collect(Collectors.toList());
    }

    public List<AuthorInfoDTO> listLivingAuthors(int year) {
        List<Author> authors = authorRepository.findAuthorsLivingInYear(year);
        return authors.stream()
                .map(a -> convertToInfoDTO(a))
                .collect(Collectors.toList());
    }

    private AuthorInfoDTO convertToInfoDTO(Author author) {
        return new AuthorInfoDTO(
                author.getName(),
                author.getBirthYear(),
                author.getDeathYear(),
                author.getBooks().stream().map(b -> b.getTitle()).collect(Collectors.toList())
        );
    }

}
