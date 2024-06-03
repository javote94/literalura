package com.aluracursos.literalura.ui;

import com.aluracursos.literalura.dtos.AuthorInfoDTO;
import com.aluracursos.literalura.dtos.BookDTO;
import com.aluracursos.literalura.dtos.BookInfoDTO;
import java.util.List;

public class Message {

    public static void showSuccessfulSaveMessage(BookDTO bookDTO) {

        if (bookDTO != null) {
            String authorName = bookDTO.getFirstAuthorDTO() != null ? bookDTO.getFirstAuthorDTO().name() : "Autor no disponible";
            String language = bookDTO.getFirstLanguage() != null ? bookDTO.getFirstLanguage() : "Idioma no disponible";
            System.out.println(
                    String.format(
                            """
                                    ------ OPERACIÓN DE GUARDADO EXITOSO -------
                                    Título:  %s
                                    Autor:  %s
                                    Lenguaje: %s
                                    Cantidad de descargas: %d
                                    --------------------------------------------
                                    """,
                            bookDTO.title(),
                            authorName,
                            language,
                            bookDTO.numberOfDownloads()
                    )
            );
        } else {
            System.out.println("La operación de guardado no se ha concretado. Por favor, vuelva a intentarlo más tarde");
        }
    }

    public static void showBooksByTitle(List<BookInfoDTO> bookInfoDTOList) {

        if (!bookInfoDTOList.isEmpty()) {
            System.out.println("**** Cantidad de coincidencias encontradas con el título dado: " + bookInfoDTOList.size() + " ****");
            for (int i = 0; i < bookInfoDTOList.size(); i++) {
                BookInfoDTO bookInfo = bookInfoDTOList.get(i);
                System.out.println(
                        String.format(
                                """
                                        ----------- DATOS DEL LIBRO [%d] -----------
                                        Título:  %s
                                        Autor:  %s
                                        Lenguaje: %s
                                        Cantidad de descargas: %d
                                        --------------------------------------------
                                        """,
                                i + 1,
                                bookInfo.title(),
                                bookInfo.authorName(),
                                bookInfo.language(),
                                bookInfo.numberOfDownloads()
                        )
                );
            }
        } else {
            System.out.println("No se ha encontrado el libro solicitado");
        }
    }

    public static void showAllBooks(List<BookInfoDTO> bookInfoDTOList) {

        if (!bookInfoDTOList.isEmpty()) {
            System.out.println("**** Cantidad de libros registrados en Literalura: " + bookInfoDTOList.size() + " ****");
            for (int i = 0; i < bookInfoDTOList.size(); i++) {
                BookInfoDTO bookInfo = bookInfoDTOList.get(i);
                System.out.println(
                        String.format(
                                """
                                        ----------- DATOS DEL LIBRO [%d] -----------
                                        Título:  %s
                                        Autor:  %s
                                        Lenguaje: %s
                                        Cantidad de descargas: %d
                                        --------------------------------------------
                                        """,
                                i + 1,
                                bookInfo.title(),
                                bookInfo.authorName(),
                                bookInfo.language(),
                                bookInfo.numberOfDownloads()
                        )
                );
            }
        } else {
            System.out.println("No hay libros registrados en Literalura");
        }
    }

    public static void showBooksByLanguage(List<BookInfoDTO> bookInfoDTOList) {

        if (!bookInfoDTOList.isEmpty()) {
            System.out.println("**** Cantidad de libros encontrados: " + bookInfoDTOList.size() + " ****");
            for (int i = 0; i < bookInfoDTOList.size(); i++) {
                BookInfoDTO bookInfo = bookInfoDTOList.get(i);
                System.out.println(
                        String.format(
                                """
                                        ----------- DATOS DEL LIBRO [%d] -----------
                                        Título:  %s
                                        Autor:  %s
                                        Lenguaje: %s
                                        Cantidad de descargas: %d
                                        --------------------------------------------
                                        """,
                                i + 1,
                                bookInfo.title(),
                                bookInfo.authorName(),
                                bookInfo.language(),
                                bookInfo.numberOfDownloads()
                        )
                );
            }
        } else {
            System.out.println("No se encontraron libros con el idioma seleccionado");
        }

    }

    public static void showAllAuthors(List<AuthorInfoDTO> authorInfoDTOList) {

        if (!authorInfoDTOList.isEmpty()) {
            System.out.println("**** Cantidad de autores registrados en Literalura: " + authorInfoDTOList.size() + " ****");
            for (int i = 0; i < authorInfoDTOList.size(); i++) {
                AuthorInfoDTO authorInfo = authorInfoDTOList.get(i);
                System.out.println(
                        String.format(
                                """
                                ----------- DATOS DEL AUTOR [%d] -----------
                                Nombre:  %s
                                Año de nacimiento:  %s
                                Año de fallecimiento: %s
                                Libros de su autoría: %s
                                --------------------------------------------
                                """,
                                i + 1,
                                authorInfo.name(),
                                authorInfo.birthYear(),
                                authorInfo.deathYear(),
                                String.join(" - ", authorInfo.bookTitles())
                        )
                );
            }
        } else {
            System.out.println("No hay autores registrados en Literalura");
        }

    }

    public static void showLivingAuthors(List<AuthorInfoDTO> authorInfoDTOList) {

        if (!authorInfoDTOList.isEmpty()) {
            System.out.println("**** Cantidad de autores vivos registrados en Literalura en el año dado: " + authorInfoDTOList.size() + " ****");
            for (int i = 0; i < authorInfoDTOList.size(); i++) {
                AuthorInfoDTO authorInfo = authorInfoDTOList.get(i);
                System.out.println(
                        String.format(
                                """
                                ----------- DATOS DEL AUTOR [%d] -----------
                                Nombre:  %s
                                Año de nacimiento:  %s
                                Año de fallecimiento: %s
                                Libros de su autoría: %s
                                --------------------------------------------
                                """,
                                i + 1,
                                authorInfo.name(),
                                authorInfo.birthYear(),
                                authorInfo.deathYear(),
                                String.join(" - ", authorInfo.bookTitles())
                        )
                );
            }
        } else {
            System.out.println("No hay autores vivos registrados en el año dado");
        }
    }

    public static void showBookNotFoundMessage(String bookTitle) {
        System.out.println("No se encontraron libros con el título '" + bookTitle + "' en la API.");
    }

    public static void showBookAlreadyExistsMessage(String bookTitle) {
        System.out.println("El libro con el título '" + bookTitle + "' ya existe en la base de datos.");
    }

    public static void showTenPopularBooks(List<BookInfoDTO> bookInfoDTOList) {

        if (!bookInfoDTOList.isEmpty()) {
            System.out.println("**** Cantidad de libros populares: " + bookInfoDTOList.size() + " ****");
            for (int i = 0; i < bookInfoDTOList.size(); i++) {
                BookInfoDTO bookInfo = bookInfoDTOList.get(i);
                System.out.println(
                        String.format(
                                """
                                        --------------- [%d °PUESTO] ---------------
                                        Título:  %s
                                        Autor:  %s
                                        Lenguaje: %s
                                        Cantidad de descargas: %d
                                        --------------------------------------------
                                        """,
                                i + 1,
                                bookInfo.title(),
                                bookInfo.authorName(),
                                bookInfo.language(),
                                bookInfo.numberOfDownloads()
                        )
                );
            }
        } else {
            System.out.println("No hay libros registrados en Literalura");
        }

    }

    public static void showBooksByAnAuthor(List<BookInfoDTO> bookInfoDTOList) {
        if (!bookInfoDTOList.isEmpty()) {
            System.out.println("**** Cantidad de libros registrados del autor [" + bookInfoDTOList.get(0).authorName() + "]: " + bookInfoDTOList.size() + " ****");
            for (int i = 0; i < bookInfoDTOList.size(); i++) {
                BookInfoDTO bookInfo = bookInfoDTOList.get(i);
                System.out.println(
                        String.format(
                                """
                                        ----------- DATOS DEL LIBRO [%d] -----------
                                        Título:  %s
                                        Autor:  %s
                                        Lenguaje: %s
                                        Cantidad de descargas: %d
                                        --------------------------------------------
                                        """,
                                i + 1,
                                bookInfo.title(),
                                bookInfo.authorName(),
                                bookInfo.language(),
                                bookInfo.numberOfDownloads()
                        )
                );
            }
        } else {
            System.out.println("No hay libros registrados del autor solicitado en Literalura");
        }

    }
}
