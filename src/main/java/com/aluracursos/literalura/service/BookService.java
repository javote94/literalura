package com.aluracursos.literalura.service;

import com.aluracursos.literalura.api.ApiClient;
import com.aluracursos.literalura.dtos.AuthorDTO;
import com.aluracursos.literalura.dtos.BookDTO;
import com.aluracursos.literalura.model.Author;
import com.aluracursos.literalura.model.Book;
import com.aluracursos.literalura.repository.AuthorRepository;
import com.aluracursos.literalura.repository.BookRepository;
import com.aluracursos.literalura.util.JsonConverter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private ApiClient apiClient;
    @Autowired
    private JsonConverter jsonConverter;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;

    private final String URL_BASE = "https://gutendex.com/books/?search=";

    @Transactional
    public BookDTO loadBookIntoDatabase(String bookTitle) {

        // 1. Consumir la API y obtener la respuesta JSON como una cadena
        var json = apiClient.requestData(URL_BASE + bookTitle);
        System.out.println(json);

        // 2. Parsear el JSON y obtener el árbol de nodos JsonNode
        JsonNode rootNode;
        try {
            rootNode = objectMapper.readTree(json);
        } catch (Exception e) {
            throw new RuntimeException("Error parsing JSON response", e);
        }

        // 3. Verificar si el array "results" (que es un array de Books) está vacío
        JsonNode resultsNode = rootNode.path("results");
        if (!resultsNode.elements().hasNext()) {
            return null;
        }

        // 3. Del array "results" capturamos el primer resultado
        JsonNode firstResultNode = resultsNode.elements().next();

        // 4. Deserializar el primer resultado a BookDTO
        BookDTO bookDTO = jsonConverter.deserialize(firstResultNode.toString(), BookDTO.class);
        System.out.println(bookDTO);

        // 5. Del bookDTO se captura el primer resultado del array "authors" y se instancia un author
        AuthorDTO authorDTO = bookDTO.getFirstAuthorDTO();
        System.out.println(authorDTO);

        // 6. Buscar si un autor con el mismo nombre ya existe en la base de datos
        Optional<Author> existingAuthor = authorRepository.findByName(authorDTO.name());
        Author author;
        if (existingAuthor.isPresent()) {  //si el autor existe, se utiliza al entidad existente
            author = existingAuthor.get();
            System.out.println(author);
        } else {
            author = new Author(authorDTO);  //si no existe, crear nueva instancia y persistirlo
            authorRepository.save(author);
        }

        // 7. Instanciar un Book con los datos de BookDTO y Author
        Book book = new Book(bookDTO, author);
        System.out.println(book);

        // 8. Persistir el Book utilizando el autor existente o el nuevo autor, según corresponda.
        bookRepository.save(book);
        return bookDTO;
    }

    public List<BookDTO> findBooksByTitlePart(String bookTitle) {
        List<Book> books = bookRepository.findByTitleContainingIgnoreCase(bookTitle);
        return books.stream()
                .map(b -> convertToDTO(b))
                .collect(Collectors.toList());
    }

    public List<BookDTO> listAllBooks() {
        List<Book> books = bookRepository.findAll();
        return books.stream()
                .map(b -> convertToDTO(b))
                .collect(Collectors.toList());
    }

    private BookDTO convertToDTO(Book book) {
        //Primero crear authorDTO asociado al bookDTO
        AuthorDTO authorDTO = new AuthorDTO(
                book.getAuthor().getName(),
                book.getAuthor().getBirthYear(),
                book.getAuthor().getDeathYear()
        );

        //Crear bookDTO
        return new BookDTO(
                book.getTitle(),
                List.of(book.getLanguage()),
                book.getNumberOfDownloads(),
                List.of(authorDTO)
        );
    }



}
