package com.ms.mongo.repository;

import java.util.List;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.ms.mongo.model.Client;

@Repository
public class ClientRepositoryImpl implements ClientRepository {

	private MongoOperations mongoOperations;
		
	public ClientRepositoryImpl() {}
	
    public ClientRepositoryImpl(MongoOperations mongoOperations) {
		System.out.println("##=> cargando... ClientRepositoryImpl ");
        this.mongoOperations = mongoOperations;
    }
	
	@Override
	public List<Client> findAllClient() {		
		List<Client> clients = this.mongoOperations.find(new Query(), Client.class);
		System.out.println("##=> ClientRepositoryImpl - findAllClient - clients: " + clients);
        return clients;
	}

	@Override
	public Client saveClient(Client client) {
		 this.mongoOperations.save(client);
		 Client newClient = findOne(client.getClientId());
		 System.out.println("##=> ClientRepositoryImpl - saveClient - Cliente creado: " + newClient);
		 return newClient;
	}

	@Override
	public void updateClient(Client client) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteClient(String id) {
		// TODO Auto-generated method stub

	}
	
	private Client findOne(String clientId) {
	    return this.mongoOperations.findOne(new Query(Criteria.where("clientId").is(clientId)), Client.class);
	}

}
