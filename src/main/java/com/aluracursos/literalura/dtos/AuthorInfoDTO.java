package com.aluracursos.literalura.dtos;

import java.util.List;

public record AuthorInfoDTO(

        String name,

        Integer birthYear,

        Integer deathYear,

        List<String> bookTitles
) {
}
