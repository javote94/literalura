package com.aluracursos.literalura.ui;

import com.aluracursos.literalura.dtos.BookDTO;

public class Message {

    public static void showMessageOption1(BookDTO bookDTO) {

        String authorName = bookDTO.getFirstAuthorDTO() != null ? bookDTO.getFirstAuthorDTO().name() : "Autor no disponible";
        String language = bookDTO.getFirstLanguage() != null ? bookDTO.getFirstLanguage() : "Idioma no disponible";

        System.out.println(
                String.format(
                        """
                        ------ OPERACIÓN DE GUARDADO EXITOSA -------
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
    }

    public static void showMessageOption2(BookDTO bookDTO, int counter) {

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
                        counter,
                        bookDTO.title(),
                        bookDTO.getFirstAuthorDTO().name(),
                        bookDTO.getFirstLanguage(),
                        bookDTO.numberOfDownloads()
                )
        );

    }
}
