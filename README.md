# ClientMongoApi
MicroServicio Spring boot - cloud - Rest - Mongo DB. Operaciones de clientes (CRUD)

Crear un microservicio spring boot/cloud que utilizará Spring Data MongoDB para crear una aplicación que procesa (CRUD) datos en MongoDB. 

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

Los microservicios se definen como un estilo arquitectural, es decir, una forma de desarrollar una aplicación, basada en un conjunto de pequeños servicios, cada uno de ellos ejecutándose de forma autónoma y comunicándose entre si mediante mecanismos livianos, generalmente a través de peticiones REST sobre HTTP por medio de sus APIs.

La tendencia es que las aplicaciones sean diseñadas con un enfoque orientado a microservicios, construyendo múltiples servicios que colaboran entre si, en lugar del enfoque monolítico, donde se construye y despliega una única aplicación que contenga todas las funcionalidades.

Se implementará un Microservicio que consuma MongoDB a trvés de Spring boot; luego utilizaremos Eureka (EurekaService), como servidor de registro y descubrimiento de microservicios. Eureka está incorporado dentro de Spring Cloud.

#### Paquetes, clases, interfaces, css y js que componen el proyecto. Java - Spring boot/cloud, maven y AngularJS.

![navigator](https://user-images.githubusercontent.com/7141537/43241089-3cdfec90-905f-11e8-96c3-22cd50116a38.png)

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
**3. Crear Excepciones**

En el caso de no localizar un cliente o de error, se lanzará una excepción.

```
public class ClientException extends Exception {
	private static final long serialVersionUID = -5512024050035997366L;
	public ClientException(String clientId) {
        super(String.format("No se encontro el usuario con identificador: '%s'", clientId));
    }
}
```

**4. Crear Controlador**

A continuación la implementación del CleintController

```
@Controller
public class ClientController {
	
    private static final Log log = LogFactory.getLog(ClientController.class);
	 
    private ClientService clientService;
    
    public ClientController() {}

    public ClientController(ClientService clientService) {
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
}
```
>  La petición RestFul @RequestMapping("/client/findAll") => Consultar todos los clientes de la base de datos
>  La petición @RequestMapping("/client") => Despliega la vista **client.html**, permite consultar crear o actualizar clientes
> La anotación @ApiOperation permitirá documentar luego los servicios RestFuk expuestos.

**5. Crear Aplicación**

Clase que ejecute la aplicación Spring Boot:

@SpringBootApplication		// Indica que se trata de una aplicación Spring Boot

```
public class ClientMongoApiApplication {
    public static void main(String[] args) {
	SpringApplication.run(ClientMongoApiApplication.class, args);
    }
}
```

**6. Crear clase que configura la aplicación**

Clase que permite la inyección y creación de objetos requeridos. 
* Cliente MongoDB
* Template Rest
* Repositorio
* Servicio
* Controlador
```
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
```

**7. Archivo de propiedades**

Por defecto, cuando inicias una aplicación spring boot , se busca un fichero llamado application.properties o _**application.yml**_ para acceder a su configuración, el cual deberá estar ubicado en la carpeta resources de nuestro proyecto. Su configuración es la siguiente:


```
# Spring properties
spring:
  data:
    mongodb:
      host: localhost
      port: 27017
      uri: mongodb://localhost/test
  application:
    name: client-service  # Service registers under this name
  thymeleaf:
    cache: false       # Allow Thymeleaf templates to be reloaded at runtime
    prefix: classpath:/templates/    # Trailing / mandatory
                       # Template location for this application only

# HTTP Server
server:
  port: 8888   # HTTP (Tomcat) port

```

**8. Vista para consultar, crear o actualizar clientes**

Uso de AngularJS, Boopstrap, css y js. Ventana de clientes

```
<!DOCTYPE html>
<html ng-app="MyApp">
	<head>
	    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />	    
	    
	    <!-- Libreria CCS bootstrap -->
	    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"></link>
	    
	    <!-- Libreria CCS propia del aplicativo -->
	    <link rel="stylesheet" href="../../css/style.css"></link>
	    
	    <!-- Libreria Jquery - requerida por boopstrap -->
	    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	    
	    <!-- Libreria bootstrap -->
	    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	    
	    <!-- Libreria angularJS -->
	    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.6/angular.min.js"></script>
	    
	    <!-- Libreria propia del aplicativo -->
	    <script src="../../js/app.js"></script>
	    
	    <title>MicroserviciosAPP</title>
	</head>
	<body ng-controller="MyController">
		<h1>Clientes</h1>
		<hr></hr>
		<div class="container">
		  <div class="row">
		  	<div class="col-sm-8">
		  		<button ng-click="getCustomer()" class ="btn btn-info">Todos los Clientes</button>
		  		<br></br>
				<br></br>
		  		<div class="row" ng-show="showData">
		  			<table class="table table-striped">
		  				<thead>
		  					<tr>
		  						<th>ID</th>
		  						<th>NOMBRE</th>
		  						<th>TELEFONO</th>
		  						<th>CORREO</th>
		  					</tr>
		  				</thead>
		  				<tbody>
		  					<tr ng-repeat="client in clients">
		  						<td>{{client.clientId}}</td>
		  						<td>{{client.name}}</td>
		  						<td>{{client.phone}}</td>
		  						<td>{{client.email}}</td>
		  					</tr>
		  				</tbody>
		  			</table>
		  		</div>
		  	</div>
		</div>
	  </div>
	  
	  <!-- Modal -->
      	  <div ng-include="'/view/modal.html'"></div
	</body>	
</html>
```

**9. Ventana modal, crear o actualizar clientes**

Uso de Boopstrap y jquery para desplegar vista modal.

```
<div class="modal fade" id="recordModal" tabindex="1" role="dialog"
aria-labelledby="myModalLabel" aria-hidden="false">
<div class="modal-dialog">
  <div class="modal-content">

    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">
            <span aria-hidden="true">&times;</span>
            <span class="sr-only">Close</span>
        </button>
      <h4 class="modal-title" id="myModalLabel">Agregar / Modificar Cliente</h4>
    </div>

    <div class="modal-body">
        <form name="recordForm" id="recordForm">
            <p>Datos del cliente</p>

            <div class="form-group">
                <label for="clientId">C&eacutedula</label>
                <div class="input-group">
                    <div class="input-group-addon">
                        <i class="glyphicon glyphicon-user"></i>
                    </div>
                    <input ng-model="record.clientId" name="clientId" id="clientId"
                    class="form-control" type="text"
                    placeholder="N&uacute;mero de identidad"></input>
                </div>
            </div>

            <div class="form-group">
                <label for="name">Nombre</label>
                <div class="input-group">
                    <div class="input-group-addon">
                        <i class="glyphicon glyphicon-stats"></i>
                    </div>
                    <input ng-model="record.name" name="name" id="name"
                    class="form-control" type="text"
                    placeholder="Nombre completo"></input>
                </div>
            </div>

            <div class="form-group">
                <label for="phone">Tel&eacutefono</label>
                <div class="input-group">
                    <div class="input-group-addon">
                        <i class="glyphicon glyphicon-link"></i>
                    </div>
                    <input ng-model="record.phone" name="phone" id="phone"
                    class="form-control" type="text"
                    placeholder="N&uacute;mero de tel&eacute;fono"></input>
                </div>
            </div>

            <div class="form-group">
                <label for="email">Correo</label>
                <div class="input-group">
                    <div class="input-group-addon">
                        <i class="glyphicon glyphicon-link"></i>
                    </div>
                    <input ng-model="record.email" name="email" id="email"
                    class="form-control" type="text"
                    placeholder="Correo"></input>
                </div>
            </div>

        </form>
    </div>
    <div class="modal-footer">
      <button type="button" class="btn btn-success" data-dismiss="modal">Cerrar</button>
      <button ng-click="addCustomer()" type="button" class="btn btn-success">Procesar</button>
    </div>
  </div>
</div>
</div>
```

**10. Modulo y controlador de AngularJS**

Uso AngularJS para controlde clientes a nivel de vista. Modelo MVC.

```
// Modulo angular
// scope: Alcance de variables 
// hhtp: libreria HTPP consulta de servicios REST
angular.module("MyApp", [])
    .controller("MyController",["$scope", "$http", function($scope, $http){
    	
    	$scope.showData = false; 	// Variable para mostrar resultados
    	$scope.clients = {};     	// Clientes encontrados en la consulta
    	$scope.record = {};     	// Clientes actual
    	
    	// Permite consultar clientes - protocolo HTTP
    	$scope.getCustomer = function(){
    	  $http.get("/client/findAll")        // URI de llamada
    	  .then(function onSuccess(response) {     // Respuesta OK
    		$scope.showData = true;
    	    $scope.clients=response.data;
    	    console.log("##=> clientes: ", $scope.clients);
    	  }).catch(function onError(response) {   // Respuesta Error
    		$scope.showData = false;
    	    console.log("##=> Error: ", response);
    	  });
    	  $('#recordModal').modal('show');
    	};

    	// Permite agregar un cliente - protocolo HTTP
    	$scope.addCustomer = function(){
    	  // URI de llamada
    	   $http({
    		   url: "/client/save",
    		   method: "POST",
    		   data: JSON.stringify($scope.record),
    		   headers: {"Content-Type": "application/json"}
    	   })
    	  .then(function onSuccess(response) {     // Respuesta OK
    		$scope.showData = true;
    	    $scope.clients=response.data;
    	    console.log("##=> clientes: ", $scope.clients);
    	  }).catch(function onError(response) {   // Respuesta Error
    		$scope.showData = false;
    	    console.log("##=> Error: ", response);
    	  });
    	   $scope.record = {};
    	  $('#recordModal').modal('hide');
    	};

    	    	 
      /**
       * Permite mostrar la ventana modal para ingresar datos a procesar
       */
       $scope.showModal = function(){
    	  $('#recordModal').modal('show');
       }
    	
    }]);
```
##### Levantar servicio y probar
> Recordar levantar mongoDB previamente.

![clientemongoapi](https://user-images.githubusercontent.com/7141537/43179721-f3fe16de-8f99-11e8-878f-5292594db7de.png)

##### Resultado en MongoDB

![clientemongoapi-1](https://user-images.githubusercontent.com/7141537/43179765-326f2cc8-8f9a-11e8-97d3-9aa362b17a1d.png)
