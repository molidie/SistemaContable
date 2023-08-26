package ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication (exclude = {SecurityAutoConfiguration.class})
public class SistemaContableApplication {

	public static void main(String[] args) {
		SpringApplication.run(SistemaContableApplication.class, args);
	}

}
