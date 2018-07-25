package com.ms.mongo.repository;

import java.util.List;

import com.ms.mongo.model.Client;

public interface ClientRepository {
	
	// Consultar todos los clientes de BD-Mongo
	public List<Client> findAllClient();
	
	// Agregar un cliente en de BD-Mongo
	public Client saveClient(Client client);
	
	// Actualizar un cliente en de BD-Mongo
    public void updateClient(Client client);
	   
 // borrar un cliente en de BD-Mongo
	public void deleteClient(String id);
}
