package com.aluracursos.literalura.service;

import com.aluracursos.literalura.api.ApiClient;
import com.aluracursos.literalura.api.ChatGPTService;
import com.aluracursos.literalura.dtos.AuthorDTO;
import com.aluracursos.literalura.dtos.BookDTO;
import com.aluracursos.literalura.dtos.BookInfoDTO;
import com.aluracursos.literalura.exceptions.BookAlreadyExistsException;
import com.aluracursos.literalura.exceptions.BookNotFoundException;
import com.aluracursos.literalura.model.Author;
import com.aluracursos.literalura.model.Book;
import com.aluracursos.literalura.repository.AuthorRepository;
import com.aluracursos.literalura.repository.BookRepository;
import com.aluracursos.literalura.util.JsonConverter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
    @Autowired
    private ChatGPTService chatGPTService;
    private final String URL_BASE = "https://gutendex.com/books/?search=";

    public BookDTO loadBookIntoDatabase(String bookTitle) {
        // Encapsula la lógica de consumo de la API y deserialización del JSON a DTO.
        BookDTO bookDTO = fetchBookFromApi(bookTitle.replace(" ", "+"));
        if (bookDTO == null) {
            // Si no se encuentra el libro en la API, lanza una excepción y detiene el flujo
            throw new BookNotFoundException();
        }
        try {
            // Encapsula la lógica de persistencia del libro, incluyendo la búsqueda o creación del autor.
            persistBook(bookDTO);
            return bookDTO;
        } catch (DataIntegrityViolationException e) {
            // Si hay un conflicto de integridad (libro duplicado), lanza una excepción y detiene el flujo
            throw new BookAlreadyExistsException();
        }
    }

    public List<BookInfoDTO> findBooksByTitlePart(String bookTitle) {
        List<Book> books = bookRepository.findByTitleContainingIgnoreCase(bookTitle);
        return books.stream()
                .map(b -> convertToInfoDTO(b))
                .collect(Collectors.toList());
    }

    public List<BookInfoDTO> listAllBooks() {
        List<Book> books = bookRepository.findAll();
        return books.stream()
                .map(b -> convertToInfoDTO(b))
                .collect(Collectors.toList());
    }

    public List<BookInfoDTO> findBooksByLanguage(String language) {
        List<Book> books = bookRepository.findByLanguageIgnoreCase(language.trim());
        return books.stream()
                .map(b -> convertToInfoDTO(b))
                .collect(Collectors.toList());

    }

    public List<BookInfoDTO> listTenPopularBooks() {
        List<Book> booksTop = bookRepository.listTenPopularBooks();
        return booksTop.stream()
                .map(b -> convertToInfoDTO(b))
                .collect(Collectors.toList());
    }

    public List<BookInfoDTO> getAuthorBibliography(String authorName) {
        List<Book> books = bookRepository.listBooksByAnAuthor(authorName);
        return books.stream()
                .map(b -> convertToInfoDTO(b))
                .collect(Collectors.toList());
    }

    public String resumeBookWithChatGPT(String bookTitle) {
        List<Book> books = bookRepository.findByTitleContainingIgnoreCase(bookTitle);
        if (books.isEmpty()) {
            throw new BookNotFoundException();
        }
        try {
            return chatGPTService.makeBookSummary(books.get(0).getTitle());
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener el resumen del libro: " + e.getMessage(), e);
        }
    }

    // ******************************** METODOS PRIVADOS ********************************

    private BookDTO fetchBookFromApi(String bookTitle) {
        // Consumir la API y obtener la respuesta JSON como una cadena
        var json = apiClient.requestData(URL_BASE + bookTitle);

        // Parsear el JSON y obtener el árbol de nodos JsonNode
        JsonNode rootNode;
        try {
            rootNode = objectMapper.readTree(json);
        } catch (Exception e) {
            throw new RuntimeException("Error parsing JSON response", e);
        }

        // Verificar si el array "results" (que es un array de Books) está vacío
        JsonNode resultsNode = rootNode.path("results");
        if (!resultsNode.elements().hasNext()) {
            return null;
        }

        // Del array "results" capturamos el primer resultado
        JsonNode firstResultNode = resultsNode.elements().next();

        // Deserializar el primer resultado a BookDTO
        return jsonConverter.deserialize(firstResultNode.toString(), BookDTO.class);
    }

    @Transactional
    private void persistBook(BookDTO bookDTO) {
        // Del bookDTO se captura el primer resultado del array "authors" y se instancia un author
        AuthorDTO authorDTO = bookDTO.getFirstAuthorDTO();

        // Buscar si un autor con el mismo nombre ya existe en la base de datos
        Optional<Author> existingAuthor = authorRepository.findByName(authorDTO.name());
        Author author;
        if (existingAuthor.isPresent()) {  //si el autor existe, se utiliza al entidad existente
            author = existingAuthor.get();
        } else {
            author = new Author(authorDTO);  //si no existe, crear nueva instancia y persistirlo
            author = authorRepository.save(author);
        }

        // Instanciar un Book con los datos de BookDTO y Author
        Book book = new Book(bookDTO, author);

        // Persistir el Book utilizando el autor existente o el nuevo autor, según corresponda.
        bookRepository.save(book);
    }

    private BookInfoDTO convertToInfoDTO(Book book) {
        return new BookInfoDTO(
                book.getTitle(),
                book.getAuthor().getName(),
                book.getLanguage(),
                book.getNumberOfDownloads()
        );
    }

}
