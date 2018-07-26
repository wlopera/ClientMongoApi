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
![clientemongoapi](https://user-images.githubusercontent.com/7141537/43179721-f3fe16de-8f99-11e8-878f-5292594db7de.png)

### Resultado en MongoDB
![clientemongoapi-1](https://user-images.githubusercontent.com/7141537/43179765-326f2cc8-8f9a-11e8-97d3-9aa362b17a1d.png)
