package com.aluracursos.literalura.dtos;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BookDTO(

    String title,

    List<String> languages,

    @JsonAlias("download_count") Integer numberOfDownloads,

    List<AuthorDTO> authors

) {

    public String getFirstLanguage() {
        return languages != null && !languages.isEmpty() ? languages.get(0) : null;
    }

    public AuthorDTO getFirstAuthorDTO() {
        return authors != null && !authors.isEmpty() ? authors.get(0) : null;
    }

}
