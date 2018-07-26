package com.ms.mongo.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.web.client.RestTemplate;

import com.mongodb.MongoClient;
import com.ms.mongo.controller.ClientController;
import com.ms.mongo.repository.ClientRepository;
import com.ms.mongo.repository.ClientRepositoryImpl;
import com.ms.mongo.service.ClientService;
import com.ms.mongo.service.ClientServiceImpl;

@Configuration
public class RestConfiguration {

	@Bean
	public MongoDbFactory mongoDbFactory() throws Exception {
		return new SimpleMongoDbFactory(new MongoClient(), "clientdb");
	}

	@Bean
	public MongoTemplate mongoTemplate() throws Exception {		
		MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory());				
		return mongoTemplate;		
	}
	
	@LoadBalanced  // Asegúrese de inyectar la plantilla de carga correcta
	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	@Bean
	ClientRepository clientRepository() throws Exception {
		return new ClientRepositoryImpl(mongoTemplate());
	} 
	
	@Bean
	ClientService clientService() throws Exception {
		return new ClientServiceImpl(clientRepository());
	} 
	
	@Bean
	ClientController clientController() throws Exception {
		return new ClientController(clientService() );
	}
}
