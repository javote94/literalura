package com.aluracursos.literalura.ui;

import com.aluracursos.literalura.dtos.AuthorInfoDTO;
import com.aluracursos.literalura.dtos.BookDTO;
import com.aluracursos.literalura.dtos.BookInfoDTO;
import com.aluracursos.literalura.exceptions.BookAlreadyExistsException;
import com.aluracursos.literalura.exceptions.BookNotFoundException;
import com.aluracursos.literalura.service.AuthorService;
import com.aluracursos.literalura.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

@Component
public class Menu {

    @Autowired
    private BookService bookService;
    @Autowired
    private AuthorService authorService;
    @Autowired
    private Scanner read;

    public void showMenu() {

        int menuOption = -1;
        String bookTitle;
        String language;
        int year;
        String authorName;
        BookDTO bookDTO;
        List<BookInfoDTO> bookInfoDTOList;
        List<AuthorInfoDTO> authorInfoDTOList;

        System.out.println(
                """
                                        
                        ********************************************************************
                        *************************** LITERALURA *****************************
                        ********************************************************************
                                        
                        Bienvenido a Literalura. Una aplicación que consume la API de Gutendex
                        para proveer al usuario de información sobre una amplia colección de
                        libros y autores disponibles en Project Gutenberg.
                        """
        );

        do {
            try {
                System.out.println(
                        """
                                                        
                                --------- MENÚ DE OPCIONES ---------
                                1. Cargar un libro desde la API de Gutendex a Literalura
                                2. Buscar un libro por título
                                3. Listar libros registrados
                                4. Listar libros según idioma
                                5. Listar libros según autor
                                6. Listar los 10 libros más descargados
                                7. Listar autores registrados
                                8. Listar autores vivos en un determinado año
                                9. Solicitar a chatGPT la síntesis de un libro
                               
                                0. Salir de la aplicación
                                ------------------------------------
                                """
                );
                System.out.print("Por favor, ingrese una opción del menú: ");
                menuOption = Integer.parseInt(read.nextLine().trim());

                switch (menuOption) {
                    case 1:
                        System.out.println("Por favor, ingrese el título del libro que desea cargar en Literalura:");
                        bookTitle = read.nextLine();
                        try {
                            bookDTO = bookService.loadBookIntoDatabase(bookTitle);
                            Message.showSuccessfulSaveMessage(bookDTO);
                        } catch (BookNotFoundException e) {
                            Message.showBookNotFoundMessage(bookTitle);
                        } catch (BookAlreadyExistsException e) {
                            Message.showBookAlreadyExistsMessage(bookTitle);
                        }
                        break;
                    case 2:
                        System.out.println("Por favor, ingrese el título del libro que desea buscar:");
                        bookTitle = read.nextLine();
                        bookInfoDTOList = bookService.findBooksByTitlePart(bookTitle);
                        Message.showBooksByTitle(bookInfoDTOList);
                        break;
                    case 3:
                        bookInfoDTOList = bookService.listAllBooks();
                        Message.showAllBooks(bookInfoDTOList);
                        break;
                    case 4:
                        System.out.println("""
                                --------- MENÚ DE LENGUAJES ---------
                                es - español
                                en - inglés
                                fr - francés
                                pt - portugués
                                ------------------------------------
                                Por favor, seleccionar un idioma para buscar libros:
                                """);
                        language = read.nextLine();
                        bookInfoDTOList = bookService.findBooksByLanguage(language);
                        Message.showBooksByLanguage(bookInfoDTOList);
                        break;
                    case 5:
                        System.out.println("Por favor, ingrese el nombre del autor para mostrar su bibliografía: ");
                        authorName = read.nextLine();
                        bookInfoDTOList = bookService.getAuthorBibliography(authorName);
                        Message.showBooksByAnAuthor(bookInfoDTOList);
                        break;
                    case 6:
                        bookInfoDTOList = bookService.listTenPopularBooks();
                        Message.showTenPopularBooks(bookInfoDTOList);
                        break;
                    case 7:
                        authorInfoDTOList = authorService.listAllAuthors();
                        Message.showAllAuthors(authorInfoDTOList);
                        break;
                    case 8:
                        System.out.println("Por favor, ingrese el año para buscar los autores vivos en la fecha dada: ");
                        year = Integer.parseInt(read.nextLine());
                        authorInfoDTOList = authorService.listLivingAuthors(year);
                        Message.showLivingAuthors(authorInfoDTOList);
                        break;
                    case 9:
                        System.out.println("Por favor, ingrese el título del libro que desea resumir: ");
                        bookTitle = read.nextLine();
                        try {
                            String resume = bookService.resumeBookWithChatGPT(bookTitle);
                            System.out.println(resume);
                        } catch (Exception e) {
                            System.out.println("Hubo un error al obtener el resumen del libro: " + e.getMessage());
                        }
                        break;
                    case 0:
                        System.out.println("Saliendo de la aplicación...");
                        break;
                    default:
                        System.out.println("Opción inválida. Por favor, ingrese una opción disponible en el menú");
                }

            } catch (InputMismatchException | NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, ingrese una opción disponible en el menú");
                continue;  //Continuar con la próxima iteración del bucle
            }

        } while (continueInMenu(menuOption));

        System.out.println("\nLe agradecemos por su visita. Vuelva pronto!");
    }

    //Verificar condición del bucle del menú
    private boolean continueInMenu(int menuOption) {
        if (menuOption >= 1 && menuOption <= 9) {
            while (true) {  //Añadir bucle para reintentar hasta obtener una respuesta válida
                try {
                    System.out.println(
                            """
                                    ------------------------------------
                                    Desea volver al menú de la aplicación?
                                    1. Si
                                    2. No
                                    """
                    );
                    System.out.print("Por favor, ingrese una respuesta: ");
                    int backToMenu = Integer.parseInt(read.nextLine().trim());

                    if (backToMenu == 1 || backToMenu == 2) {
                        return backToMenu == 1;  //Retorna TRUE si la respuesta es 1, FALSE si es 2
                    } else {
                        System.out.println("Entrada inválida. Por favor, ingrese 1 para Sí o 2 para No.");
                    }

                } catch (InputMismatchException | NumberFormatException e) {
                    System.out.println("Entrada inválida. Por favor, ingrese 1 para Sí o 2 para No.");
                }
            }
        }
        if (menuOption > 9 || menuOption < 0) {
            return true;
        }
        return false;
    }
}
