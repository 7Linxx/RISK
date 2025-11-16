package co.edu.unbosque.backRisk;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Clase principal de la aplicación Spring Boot.
 * <p>
 * Contiene el método main que inicia la aplicación y define beans de
 * configuración a nivel de aplicación (por ejemplo ModelMapper).
 * </p>
 * 
 * @author Mariana Pineda
 * @version 2.0
 */
@SpringBootApplication
public class BackRiskApplication {

	/**
	 * Punto de entrada de la aplicación Spring Boot.
	 * 
	 * @param args argumentos de línea de comandos pasados al iniciar la JVM.
	 */
	public static void main(String[] args) {
		SpringApplication.run(BackRiskApplication.class, args);
	}

	/**
	 * Proporciona una instancia compartida de ModelMapper como bean de Spring.
	 * <p>
	 * ModelMapper se usa para mapear entre entidades y DTOs en los servicios.
	 * </p>
	 * 
	 * @return una nueva instancia de ModelMapper gestionada por Spring.
	 */
	@Bean
	public ModelMapper getModelMapper() {
		return new ModelMapper();
	}
}