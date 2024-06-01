package com.aluracursos.literalura;

import com.aluracursos.literalura.api.ApiClient;
import com.aluracursos.literalura.service.BookService;
import com.aluracursos.literalura.ui.Menu;
import com.aluracursos.literalura.util.JsonConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

	@Autowired
	private Menu menu;

	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		menu.showMenu();
	}
}
