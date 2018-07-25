package com.ms.mongo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@EnableAutoConfiguration 	// Configura la aplicación sin necesidad de otros ficheros de configuración XML, etc.
//@EnableDiscoveryClient   	// Habilita el servicio de registro y descubrimiento. 
							// Permite que nuestros servicios se suscriban automáticamente en el servidor Eureka.
@SpringBootApplication		// Indica que se trata de una aplicación Spring Boot
public class ClientMongoApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClientMongoApiApplication.class, args);
	}
}
