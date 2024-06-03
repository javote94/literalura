package com.aluracursos.literalura.dtos;

public record BookInfoDTO(

        String title,

        String authorName,

        String language,

        Integer numberOfDownloads

) {
}
