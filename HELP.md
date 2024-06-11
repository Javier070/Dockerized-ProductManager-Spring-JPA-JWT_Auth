

## Introducción

Este documento proporciona una guía detallada sobre cómo utilizar y configurar la aplicación. Aquí encontrarás información sobre los endpoints disponibles, cómo autenticarse y ejemplos de uso.



### Endpoints

- **GET /products**: Obtiene una lista de todos los productos.
- **POST /products**: Añade un nuevo producto.
- **PUT /products/{id}**: Actualiza un producto existente.
- **DELETE /products/{id}**: Elimina un producto.
- **GET /categories**: Obtiene una lista de todas las categorías.
- **POST /categories**: Añade una nueva categoría.
- **PUT /categories/{id}**: Actualiza una categoría existente.
- **DELETE /categories/{id}**: Elimina una categoría.

### Autenticación

La autenticación se realiza mediante tokens JWT. Para acceder a la mayoría de los endpoints, es necesario incluir un token JWT válido en el encabezado de la solicitud.

```http
Authorization: Bearer <TOKEN_JWT>
```

### Ejemplos de Solicitudes

#### Obtener todos los productos

```http
GET /products HTTP/1.1
Host: localhost:8080
Authorization: Bearer <TOKEN_JWT>
```

#### Añadir un nuevo producto

```http
POST /products HTTP/1.1
Host: localhost:8080
Content-Type: application/json
Authorization: Bearer <TOKEN_JWT>

{
    "name": "Nuevo Producto",
    "description": "Descripción del producto",
    "price": 19.99,
    "status": "available",
    "category": {
        "id": 1
    }
}
```

## Mensajes de Error

- **Algo salió mal**: Se produjo un error general en el servidor.
- **Datos no válidos**: Los datos proporcionados en la solicitud no son válidos.
- **Acceso no autorizado**: El usuario no tiene los permisos necesarios para realizar esta operación.
- **ID incorrecto**: El ID proporcionado no existe.
- **Operación exitosa**: La operación se completó con éxito.



## Manejo de Errores

- **400 Bad Request**: Datos de solicitud no válidos.
- **401 Unauthorized**: Falta de token JWT o token inválido.
- **403 Forbidden**: Acceso denegado.
- **404 Not Found**: Recurso no encontrado.
- **500 Internal Server Error**: Error en el servidor.


#### Manjeo de errores durante el mantenimiento
- Cuando el programa da error al hacer isAdmin()  se debe a que el token esta vacio,
- el jwtFilter.isAdmin() responde status 500 cuando no has ingresado ningún usuario
  si quieres recibir el status 401 le tienes que pasar
  un user con: el status "true" y el rol "user"




## Contacto

Para más información o asistencia, por favor contacta con el desarrollador:

https://github.com/Javier070

**Nombre**: Javier Morales Martos

---



# Documentación de referencia
Para mayor referencia, considere las siguientes secciones:
* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.2.5/maven-plugin/reference/html/)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/3.2.5/reference/htmlsingle/index.html#data.sql.jpa-and-spring-data)
* [Spring Web](https://docs.spring.io/spring-boot/docs/3.2.5/reference/htmlsingle/index.html#web)
* [JDBC API](https://docs.spring.io/spring-boot/docs/3.2.5/reference/htmlsingle/index.html#data.sql)

### Guides
Las siguientes guías ilustran cómo utilizar algunas funciones de forma concreta:

* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Accessing data with MySQL](https://spring.io/guides/gs/accessing-data-mysql/)
* [Accessing Relational Data using JDBC with Spring](https://spring.io/guides/gs/relational-data-access/)
* [Managing Transactions](https://spring.io/guides/gs/managing-transactions/)


