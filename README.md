# Literalura 📚

![Status](https://img.shields.io/badge/status-en%20preparación-yellow)
![Last Commit](https://img.shields.io/badge/last%20commit-Junio%202024-blue)
![Java Version](https://img.shields.io/badge/Java-JDK%2017-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.0-brightgreen)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-13-blue)
![IDE](https://img.shields.io/badge/IDE-IntelliJ%20IDEA-purple)

## Índice
1. [Descripción del proyecto](#descripción-del-proyecto)
2. [Funcionalidades](#funcionalidades)
3. [API utilizada](#api-utilizada)
4. [Estructura del proyecto](#estructura-del-proyecto)
5. [Cómo correr la aplicación](#cómo-correr-la-aplicación)
6. [Contribuciones](#contribuciones)
7. [Agradecimientos](#agradecimientos)

## Descripción del proyecto
Literalura es una aplicación de consola desarrollada en Java con Spring Framework que permite a los usuarios interactuar con una amplia colección de libros y autores disponibles en Project Gutenberg. La aplicación consume la API de Gutendex para obtener información actualizada sobre los libros y autores.

## Funcionalidades
La aplicación ofrece las siguientes funcionalidades a través de un menú interactivo:
- **Cargar un libro desde la API de Gutendex a Literalura**: Permite al usuario agregar un libro a la base de datos local.
- **Buscar un libro por título**: Permite al usuario buscar libros por su título.
- **Listar libros registrados**: Muestra todos los libros almacenados en la base de datos local.
- **Listar libros según idioma**: Filtra y muestra libros por su idioma.
- **Listar libros según autor**: Filtra y muestra libros por el autor especificado.
- **Listar los 10 libros más descargados**: Muestra los diez libros con más descargas.
- **Listar autores registrados**: Muestra todos los autores almacenados en la base de datos local.
- **Listar autores vivos en un determinado año**: Muestra autores que estaban vivos en el año especificado.
- **Solicitar a chatGPT la síntesis de un libro**: Permite al usuario obtener un resumen breve de un libro utilizando la API de OpenAI.

## API Utilizada
Literalura utiliza las siguientes APIs:
- **[Gutendex API](https://gutendex.com/)**: Proporciona acceso a los libros y autores de Project Gutenberg.
- **[OpenAI API](https://www.openai.com/)**: Se utiliza para obtener resúmenes breves de los libros mediante chatGPT.

## Dependencias de Maven
El proyecto utiliza las siguientes dependencias de Maven:
- **Spring Boot Starter**: Dependencia principal para crear aplicaciones Spring Boot.
- **Spring Boot Starter Data JPA**: Dependencia para trabajar con JPA y bases de datos.
- **PostgreSQL**: Driver JDBC para conectarse a bases de datos PostgreSQL.
- **Lombok**: Biblioteca para reducir el código boilerplate en las clases de modelo.
- **Jackson Databind**: Biblioteca para trabajar con JSON en Java.
- **OpenAI GPT-3 Java Service**: Cliente para interactuar con la API de OpenAI.

## Estructura del proyecto
El proyecto está estructurado en varios paquetes, diseñados para separar las responsabilidades y facilitar el mantenimiento y la escalabilidad:
- **`com.aluracursos.literalura`**: Contiene la clase principal `LiteraluraApplication` que sirve como punto de entrada del programa.
- **`api`**: Contiene las clases `ApiClient` y `ChatGPTService` para la interacción con las APIs externas. `ApiClient` maneja la comunicación con la API de Gutendex y `ChatGPTService` maneja la comunicación con la API de OpenAI.
- **`model`**: Contiene las entidades `Book` y `Author` que representan los datos almacenados en la base de datos. Estas clases modelan los objetos con los que opera la aplicación, definiendo sus atributos y relaciones.
- **`service`**: Contiene las clases `BookService` y `AuthorService` que encapsulan la lógica de negocio. Estas clases manejan las operaciones principales de la aplicación, como la carga y búsqueda de libros y autores.
- **`repository`**: Define los repositorios `BookRepository` y `AuthorRepository` para la interacción con la base de datos. Estos repositorios extienden `JpaRepository` para proporcionar operaciones CRUD y consultas personalizadas.
- **`dtos`**: Define los Data Transfer Objects (DTOs) utilizados para transferir datos entre la aplicación y las APIs. Incluye clases como `BookDTO`, `AuthorDTO`, `BookInfoDTO` y `AuthorInfoDTO`.
- **`util`**: Incluye la clase `JsonConverter` para la deserialización de datos JSON. Esta clase utiliza `ObjectMapper` de Jackson para convertir JSON a objetos Java.
- **`ui`**: Contiene la clase `Menu` que gestiona la interacción directa con el usuario a través de la consola. Esta clase presenta el menú interactivo y maneja las entradas del usuario.
- **`exceptions`**: Define las excepciones personalizadas utilizadas en la aplicación, como `BookNotFoundException` y `BookAlreadyExistsException`, para manejar errores específicos de negocio.

## Cómo correr la aplicación
Para correr la aplicación en tu computadora, sigue estos pasos:

1. **Clonar el repositorio**
   - Usa Git para clonar el repositorio en tu entorno local:
     ```bash
     git clone https://github.com/javote94/literalura.git
     ```

2. **Configuración del archivo `application.properties`**
   - Crea un archivo `application.properties` en `src/main/resources/` con el siguiente contenido:
     ```properties
     spring.application.name=literalura
     spring.datasource.url=jdbc:postgresql://localhost/literalura
     spring.datasource.username=your_username
     spring.datasource.password=your_password
     spring.datasource.driver-class-name=org.postgresql.Driver
     spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
     spring.jpa.hibernate.ddl-auto=update
     openai.api.key=your_openai_api_key
     logging.level.org.hibernate.engine.jdbc.spi.SqlExceptionHelper=OFF
     ```
   - Asegúrate de reemplazar `your_username`, `your_password`, y `your_openai_api_key` con tus credenciales de PostgreSQL y la clave API de OpenAI.

3. **Preparación del entorno de trabajo**
   - Asegúrate de tener instalado Java JDK 17. Si no, puedes descargarlo e instalarlo desde el sitio web de [Oracle](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html).
   - Crea una base de datos PostgreSQL llamada `literalura`. Si no tienes PostgreSQL instalado, puedes descargarlo desde el sitio web oficial de [PostgreSQL](https://www.postgresql.org/download/).
   - También se recomienda utilizar IntelliJ IDEA para abrir y ejecutar el proyecto. Puedes descargarlo desde el sitio web de [IntelliJ IDEA](https://www.jetbrains.com/idea/download/).

4. **Ejecución del proyecto**
   - Abre el proyecto en IntelliJ IDEA.
   - Ejecuta la clase `LiteraluraApplication` para iniciar la aplicación y sigue las instrucciones del menú.

## Contribuciones
Este proyecto está en preparación. Cualquier feedback es bienvenido y si estás interesado en contribuir, estamos abiertos a pull requests o puedes [abrir un issue](https://github.com/javote94/literalura/issues) para discutir posibles cambios.

## Agradecimientos
Mis agradecimientos a las organizaciones Alura Latam y Oracle Next Education por proporcionar el contexto educativo para el desarrollo del proyecto. El apoyo y recursos de formación brindados han sido fundamentales en la realización de esta aplicación.
