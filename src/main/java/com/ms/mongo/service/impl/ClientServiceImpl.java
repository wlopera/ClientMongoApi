package com.ms.mongo.service.impl;

import java.util.List;

import com.ms.mongo.model.Client;
import com.ms.mongo.repository.ClientRepository;
import com.ms.mongo.service.ClientService;

public class ClientServiceImpl implements ClientService {

    private ClientRepository clientRepository;
 
    public ClientServiceImpl() {}
    
    public ClientServiceImpl(ClientRepository clientRepository){
    	System.out.println("##=> cargando... ClientServiceImpl ");
        this.clientRepository = clientRepository;
    }
 
    
	@Override
	public List<Client> findAllClient() {
		List<Client> clients = clientRepository.findAllClient();
        return clients;      
	}

	@Override
	public Client saveClient(Client client) {
		 return clientRepository.saveClient(client);
	}

	@Override
	public void updateClient(Client client) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteClient(String id) {
		// TODO Auto-generated method stub

	}

}
