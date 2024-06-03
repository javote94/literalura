package com.aluracursos.literalura.api;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ChatGPTService {

    private final OpenAiService service;

    // Inyectar la clave API de OpenAI desde las propiedades de configuración
    public ChatGPTService(@Value("${openai.api.key}") String apiKey) {
        this.service = new OpenAiService(apiKey);
    }

    public String makeBookSummary(String bookTitle) {

        try {
            CompletionRequest requisition = CompletionRequest.builder()
                    .model("gpt-3.5-turbo-instruct")
                    .prompt("Hacer un resumen de muy pocas líneas de texto del siguiente libro: " + bookTitle)
                    .maxTokens(300)
                    .temperature(0.7)
                    .build();

            var respuesta = service.createCompletion(requisition);
            return respuesta.getChoices().get(0).getText().trim();
        } catch (Exception e) {
            throw new RuntimeException("Error al interactuar con la API de ChatGPT: " + e.getMessage(), e);
        }
    }

}
