package com.aluracursos.literalura.ui;

import com.aluracursos.literalura.dtos.BookDTO;
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
    private Scanner read;

    public void showMenu() {

        int menuOption = 0;
        String bookTitle;
        BookDTO bookDTO;
        List<BookDTO> booksDTOs;

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
                                1. Cargar un libro nuevo
                                2. Buscar un libro por título
                                3. Listar libros registrados
                                4. Listar autores registrados
                                5. Listar autores vivos en un determinado año
                                6. Listar libros según idioma
                                0. Salir de la aplicación
                                ------------------------------------
                                """
                );
                System.out.print("Por favor, ingrese una opción del menú: ");
                menuOption = Integer.parseInt(read.nextLine().trim());

                switch (menuOption) {
                    case 1:
                        System.out.println("Por favor, ingrese el título del libro que desea cargar en Literalura:");
                        bookTitle = read.nextLine().replace(" ", "+");
                        bookDTO = bookService.loadBookIntoDatabase(bookTitle);
                        if (bookDTO != null) {
                            Message.showMessageOption1(bookDTO);
                        } else {
                            System.out.println("No se encontraron libros para el título dado");
                        }
                        break;
                    case 2:
                        System.out.println("Por favor, ingrese el título del libro que desea buscar:");
                        bookTitle = read.nextLine();
                        booksDTOs = bookService.findBooksByTitlePart(bookTitle);
                        if (!booksDTOs.isEmpty()) {
                            System.out.println("**** Cantidad de coincidencias encontradas con el título dado: " + booksDTOs.size() + " ****");
                            int counter = 0;
                            for (BookDTO dto : booksDTOs) {
                                counter++;
                                Message.showMessageOption2(dto, counter);
                            }
                        } else {
                            System.out.println("No se ha encontrado el libro solicitado");
                        }
                        break;
                    case 3:
                        booksDTOs = bookService.listAllBooks();
                        if (!booksDTOs.isEmpty()) {
                            System.out.println("**** Cantidad de libros registrados en Literalura: " + booksDTOs.size() + " ****");
                            int counter = 0;
                            for (BookDTO dto : booksDTOs) {
                                counter++;
                                Message.showMessageOption2(dto, counter);
                            }
                        } else {
                            System.out.println("No hay libros registrados en Literalura");
                        }
                        break;
                    case 4:
                        System.out.println("Opción 4");
                        break;
                    case 5:
                        System.out.println("Opción 5");
                        break;
                    case 6:
                        System.out.println("Opción 6");
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
        if (menuOption >= 1 && menuOption <= 6) {
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
        if (menuOption > 6 || menuOption < 0) {
            return true;
        }
        return false;
    }
}
