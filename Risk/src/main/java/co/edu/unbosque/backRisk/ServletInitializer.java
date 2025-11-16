package co.edu.unbosque.backRisk;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Inicializador para despliegues en contenedores Servlet tradicionales (WAR).
 * <p>
 * Extiende SpringBootServletInitializer para permitir que la aplicación se
 * despliegue en servidores que esperan una clase ServletContext inicializadora.
 * </p>
 * 
 * @author Mariana Pineda
 * @version 2.0
 */
public class ServletInitializer extends SpringBootServletInitializer {

	/**
	 * Configura la aplicación cuando se ejecuta como aplicación WAR en un
	 * contenedor Servlet externo.
	 * 
	 * @param application el builder de la aplicación a configurar.
	 * @return el builder configurado con la clase principal de la aplicación.
	 */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(BackRiskApplication.class);
	}

}