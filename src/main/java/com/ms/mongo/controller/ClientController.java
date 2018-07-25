package com.ms.mongo.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ms.mongo.model.Client;
import com.ms.mongo.service.ClientService;

import io.swagger.annotations.ApiOperation;

@Controller
public class ClientController {
	
	private static final Log log = LogFactory.getLog(ClientController.class);
	 
    private ClientService clientService;
    
    public ClientController() {}

    public ClientController(ClientService clientService) {
		System.out.println("##=> cargando... ClientController ");
        this.clientService = clientService;
    }
    
    @RequestMapping("/")
	public String home() {
		return "index";
	}
    
    @RequestMapping("/client")
    public String goHome() {    	
        return "client";
    }	
    
    @RequestMapping("/client/findAll")
    @ApiOperation(value = "Consultar todos los clientes", notes = "Retorna todos los clientes" )
    @ResponseBody
    public List<Client> findAll(){
        log.info("##=> Consultar todos los clientes");
        List<Client> clients = clientService.findAllClient();
        log.info("##=> ClientController - findAllClient - Clientes: " + clients.toString());
        return clients;
    }
    
    @RequestMapping(value = "/client/save", method=RequestMethod.POST)
    @ApiOperation(value = "Crear cliente", notes = "Crear un nuevo cliente")
    @ResponseBody
    public  List<Client> saveUser(@RequestBody @Valid Client client){
    	log.info("##=> Salvar cliente: "+ client);
    	Client newClient = clientService.saveClient(client);
		log.info("##=> ClientController - saveClient - Cliente creado: " + newClient);
    	return findAll();
    }
    
    
	@RequestMapping(value = "/client/saveDummy")
	@ApiOperation(value = "Crear cliente DUMMY", notes = "Crear un nuevo cliente de prueba")
	public ResponseEntity<Client> saveClient() {
		// Client client = new Client("8642", "Didier Drogba", "6534-4567", "drogba@test.com");
		// Client client = new Client("12345", "Lionel Messi", "3434-6567", "lmessi@test.com");
		// Client client = new Client("35790", "Cristiano Ronaldo", "5434-4689", "cronaldo@test.com");
		Client client = new Client("67890", "Neymar D'silva", "6342-4667", "neymar@test.com");
		log.info("Salvar cliente DUMMY ");
		Client newClient = clientService.saveClient(client);
		log.info("##=> ClientController - saveClient - Cliente creado DUMMY: " + newClient);
		return ResponseEntity.ok(newClient);
	}

}
