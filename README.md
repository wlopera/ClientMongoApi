# ClientMongoApi
MicroServicio Spring boot - cloud - Rest - Mongo DB. Operaciones de clientes (CRUD)

Crear un microservicio spring boot/cloud que utilizará Spring Data MongoDB para crear una aplicación que procesa (CRUD) datos de MongoDB. 

## MongoDB
Es un sistema de base de datos **NoSQL** orientado a documentos, desarrollado bajo el concepto de **código abierto**.
MongoDB forma parte de la nueva familia de sistemas de base de datos NoSQL. En lugar de guardar los datos en tablas como se hace en las base de datos relacionales, MongoDB guarda estructuras de datos en **documentos** similares a JSON con un esquema dinámico (MongoDB utiliza una especificación llamada **BSON**), haciendo que la integración de los datos en ciertas aplicaciones sea más fácil y rápida

[Instalar MongoDB!](https://docs.mongodb.com/manual/installation/)

Levanta el servidor de MongoDB:
* cmd windows: unidad\ruta_mongo\bin> **mongod**.exe.

Levantar MongoDB:
* cmd windows: unidad\ruta_mongo\bin> **mongo**.exe.
    
> Por defecto las peticiones se escuchan en el **puerto 27017**

## Microservicio

**1. Objeto de dominio**

Creamos una clase Client, modelo de base de datos

```
@Id
@ApiModel(description = "Respuesta del servico de consulta de cliente.")
@Document(collection = "client")
@JsonPropertyOrder({"clientId"})
public class Client implements Serializable{

	private static final long serialVersionUID = 1L;
		
	@ApiModelProperty(notes = "Identificador del cliente", example = "Integer", required = true, position = 0)
	@NotNull
	private String clientId;
	
	@ApiModelProperty(notes = "Identificador del cliente", example = "String", required = true, position = 1)
	@NotNull	
	private String name;
	
	@ApiModelProperty(notes = "Identificador del cliente", example = "String", required = true, position = 2)
	@NotNull
	private String phone;
	
	@ApiModelProperty(notes = "Identificador del cliente", example = "String", required = true, position = 3)
	@NotNull
	private String email;
	
	public Client() {}
	
	public Client(String clientId, String name, String phone, String email) {
		this.clientId = clientId;
		this.name = name;
		this.phone = phone;
		this.email = email;
	}
    // getters and Setters
    // Constructores con campos o por defecto (POJO)
    // Generar toString
```
> Utilizamos la anotación @Document para definir un nombre de una colección cuando el objeto se guarde en MongoDB. En este caso, cuando el objeto “cliente” se guarde, se hará dentro de la colección “client”.

> La anotación de Jackson @JsonPropertyOrder nos permite especificar el orden en que los campos del objeto java deberían ser serializados en JSON.

> Las anotaciones @ApiModelProperty son específicas de Swagger y son provistas por la dependencia que SpringFox tiene de Swagger y nos permitirán luego documentar el servicio.


**2. Crear Repositorio**

Crear la interface para definir las operaciones CRUD de el objeto de dominio **Client**

```
public interface ClientRepository {
	
	// Consultar todos los clientes de BD-Mongo
	public List<Client> findAllClient();
	
	// Agregar un cliente en de BD-Mongo
	public Client saveClient(Client client);
	
	// Actualizar un cliente en de BD-Mongo
        public void updateClient(Client client);
	   
        // Borrar un cliente en de BD-Mongo
	public void deleteClient(String id);
}
```
Seguido de la mplementación de la interface **ClientRepository**

```
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
```
**2. Crear Servicios**

Crear la interface para definir los servicios, lógica del negocio. Conecta el controlador con el repositorio.

```
public interface ClientService {
	
	// Consultar todos los clientes de BD-Mongo
	public List<Client> findAllClient();
	
	// Agregar un cliente en de BD-Mongo
	public Client saveClient(Client client);
	
	// Actualizar un cliente en de BD-Mongo
    public void updateClient(Client client);
	   
    // Borrar un cliente en de BD-Mongo
	public void deleteClient(String id);
}
```
Seguido de la mplementación de la interface **ClientService**

```
public class ClientServiceImpl implements ClientService {

	private ClientRepository clientRepository;
	
	public ClientServiceImpl() {}
	
	public ClientServiceImpl(ClientRepository clientRepository) {
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

```

![clientemongoapi](https://user-images.githubusercontent.com/7141537/43179721-f3fe16de-8f99-11e8-878f-5292594db7de.png)

### Resultado en MongoDB
![clientemongoapi-1](https://user-images.githubusercontent.com/7141537/43179765-326f2cc8-8f9a-11e8-97d3-9aa362b17a1d.png)
