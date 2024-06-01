package com.aluracursos.literalura.api;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@Component
public class ApiClient {

    private final HttpClient client;

    // Constructor para inicializar HttpClient con configuración personalizada
    public ApiClient() {
        this.client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))  //Nota(1)
                .followRedirects(HttpClient.Redirect.NORMAL)  //Nota(2)
                .build();
    }

    // Método para realizar una solicitud a la URL proporcionada
    public String requestData(String url) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofSeconds(10))  //Nota(3)
                .header("Accept", "application/json")  //Nota(4)
                .build();

        try {
            // Enviar la solicitud y obtener la respuesta
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Código de respuesta: " + response.statusCode());
            return response.body();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Error fetching data from API: " + e.getMessage(), e);
        }
    }
}

/*
Nota (1):
Se establece un tiempo de espera de conexión de 10 segundos. Esto evita que la solicitud
quede bloqueada indefinidamente si el servidor no responde
*/

/*
Nota(2):
Esta configuración permite que el cliente siga redirecciones en caso de recibir códigos
de respuesta 301 y 302 de forma automática.
En otras palabras, cuando tu aplicación realiza una solicitud a una URL que responde
con un 301 o 302, el HttpClient automáticamente lee la cabecera Location de la respuesta.
Luego, el HttpClient realiza una nueva solicitud a la URL proporcionada en la cabecera Location.
*/

/*
Nota (3):
Se establece un tiempo de espera de 10 segundos para la solicitud. Esto asegura que
la solicitud no se prolongue demasiado si el servidor tarda en responder.
*/

/*
Nota (4):
Se agrega un encabezado HTTP Accept con valor application/json. Esto le indica al
servidor que esperamos una respuesta en formato JSON.
*/


