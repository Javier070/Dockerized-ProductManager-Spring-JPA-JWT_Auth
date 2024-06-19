

## Descripción

Esta es una aplicación web diseñada para la gestión de productos y categorías. La aplicación permite
a los usuarios autenticarse y realizar operaciones. 

Si quiere tambien el front-end tenga en cuenta el ***Contenido 10*** y este enlace: https://github.com/Javier070/ProductManager-Frontend.

### Advertencia
Si tiene dificultades para la comprensión del proyecto o necesitas mas información  recoiendo encarecidamente
consultar el archvio `HELP.md`


## Contenido

1. [Descripción](#descripción)
2. [Características](#características)
3. [Tecnologías Utilizadas](#tecnologías-utilizadas)
4. [Requisitos Previos](#requisitos-previos)
5. [Instalación](#instalación)
6. [Solución de Problemas Comunes](#solución-de-problemas-comunes)
7. [Uso Básico](#uso-básico)
8. [Ejecución de los Contenedores con Docker](#ejecución-de-los-contenedores-con-docker)
9. [Ejecución de los Contenedores sin Docker Compose](#ejecución-de-los-contenedores-sin-docker-compose)
10. **👉 [Acceder a la Aplicación Completa](#acceder-a-la-aplicación-completa) 👈**
11. [Contribución](#contribución)
12. [Contacto](#contacto)

## Características

- **Gestión de Productos**
- **Gestión de Categorías**
- **Autenticación y Autorización**: Uso de JWT para proteger rutas y recursos.
- **API RESTful**: Implementación de una API siguiendo las mejores prácticas REST.

## Tecnologías Utilizadas

- **Backend**: Java con Spring Boot
- **Frontend**: HTML, CSS, JavaScript
- **Base de Datos**: MySQL
- **Seguridad**: JWT (JSON Web Tokens)

## Requisitos Previos

- **Java**
- **Maven**
- **MySQL**
- **Docker**


## Instalación

1. **Clonar el repositorio**:
   ```bash
   git clone https://github.com/tu-usuario/com.jm.tfg-main.git
   ```

2. **Construye el proyecto usando Maven**:
   ```bash
    ./mvnw clean install
   ```

3. **Configurar la base de datos (si no quieres usar docker)**:
    - Tener iniciado el servidor MySQL, en mi caso uso el proporcionado por XAMPP.
    - Crea una base de datos para el proyecto utilizando MySQL Workbench o cualquier otra herramienta de administración MySQL
    - Asegurate el archivo `application.properties` coinciden las credenciales con la base de datos.
    - La tablas se crean al ejecutar la aplicación.
    - En el archivo HELP.md encontrarás un script para rellenar las tablas.


4. **Ejecutar la aplicación**


## Solución de Problemas Comunes

### Error al Cargar el Contexto de la Aplicación (Failed to load ApplicationContext)

Si te encuentras con el siguiente error al ejecutar `mvn install` o `mvn test`:

Esto indica que la aplicación no pudo conectarse a la base de datos MySQL. Este problema suele deberse a que el servidor de base de datos no está corriendo o la configuración de la conexión es incorrecta.

#### Pasos para Resolver el Problema

1. **Verificar que el Servidor de Base de Datos esté Corriendo**:
    - Si estás usando XAMPP, asegúrate de que MySQL estén iniciado.

2. **Verificar la Configuración de la Base de Datos**:
    - Asegúrate de que la configuración en `src/main/resources/application.properties` o `src/main/resources/application.yml` sea correcta. Debería parecerse a esto:

   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/tu_base_de_datos
   spring.datasource.username=tu_usuario
   spring.datasource.password=tu_contraseña
   spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

## Uso Básico

Una vez que la aplicación esté en funcionamiento, puedes acceder a los siguientes endpoints:

Para acceder a los endpoints protegidos, es necesario incluir un token JWT en el encabezado de la solicitud HTTP.

- **Productos**:
    - `GET /products`
    - `POST /products`
    - `PUT /products/{id}`
    - `DELETE /products/{id}`

- **Categorías**:
    - `GET /categories`
    - `POST /categories`
    - `PUT /categories/{id}`
    - `DELETE /categories/{id}`
#### Entre muchos otros métodos, esto es una referencia

---
## Ejecución de los Contenedores con Docker

***Para ejecutar la aplicación utilizando contenedores Docker, sigue los pasos a continuación:***

### 1. **Descargar Docker**

Primero, asegúrate de tener Docker instalado en tu sistema. Luego, descarga la imagenes de Docker para la aplicación.

El archivo docker-compose.yml incluye la configuración necesaria para los contenedores de la aplicación y la base de datos MySQL.     
No se requiere configuración adicional.

#### 1.1 Descargar imágenes:
Si no tienes las imágenes en tu máquina local, puedes descargarlas con los siguientes comandos:
   ```bash
   docker pull ghcr.io/javier070/java-tfg:1.0.6
   ```
   ```bash
 docker pull ghcr.io/javier070/my-mysql-tfg:9.1
   ```
#### 2. Ejecutar Docker Compose

En la raíz del proyecto, ejecuta el siguiente comando para iniciar los contenedores:

 ```bash
docker-compose up
   ```
Este comando levantará los contenedores definidos en el archivo docker-compose.yml, que deberían incluir tanto la aplicación como la base de datos MySQL.


#### 3. **Verificar la Ejecución**
Para verificar que la aplicación está funcionando, puedes utilizar herramientas
como Postman para hacer peticiones a los endpoints protegidos de la API en http://localhost:8080.
Necesitarás autenticarte para acceder a estos endpoints.

#### 4. Detener los Contenedores
Para detener y eliminar los contenedores, ejecuta:
 ```bash
docker-compose down
   ```

----
## Ejecución de los Contenedores sin Docker Compose
Si prefieres no usar Docker Compose, puedes ejecutar los contenedores individualmente con los siguientes comandos:

1. Ejecutar el Contenedor MySQL

 ```bash
docker run --name mysql-tfg -e MYSQL_ROOT_PASSWORD=<PASSWORD> -e MYSQL_DATABASE=tfg -p 3306:3306 -d ghcr.io/javier070/my-mysql-tfg:9.1
   ```
Asegúrate de reemplazar <PASSWORD> con la contraseña real del usuario root de tu base de datos MySQL.

2. Ejecutar el Contenedor de la Aplicación

 ```bash
docker run --name tfg-backend50 --network host -p 8080:8080 -e SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/tfg -e SPRING_DATASOURCE_USERNAME=root -e SPRING_DATASOURCE_PASSWORD=<PASSWORD> ghcr.io/javier070/java-tfg:1.0.6
   ```
Asegúrate de reemplazar <PASSWORD> con la contraseña real del usuario root de tu base de datos MySQL.

---
# Acceder a la Aplicación Completa
Para acceder a la versión completa de la aplicación, necesitas descargar el frontend desde otro repositorio y ejecutarlo en el puerto 5500. 
Enlace al reposiotrio: https://github.com/Javier070/ProductManager-Frontend

Sigue estos pasos:

Clonar el repositorio del frontend:
 ```bash
git clone https://github.com/Javier070/ProductManager-Frontend.git
   ```

Navegar al directorio del frontend:
 ```bash
cd ProductManager-Frontend
   ```

Iniciar el servidor del frontend (por ejemplo, utilizando Live Server[recomiendo usar la extensión de Visual Studio Code]):
 ```bash
live-server --port=5500
   ```

Una vez que el servidor frontend esté en funcionamiento, accede a la aplicación completa en  http://127.0.0.1:5500/Inicio/login/login.html

---
## Contribución

Si deseas contribuir a este proyecto, por favor sigue estos pasos:

1. Haz un fork del proyecto.
2. Crea una nueva rama (`git checkout -b feature/nueva-funcionalidad`).
3. Realiza tus cambios y haz commit (`git commit -m 'Añadir nueva funcionalidad'`).
4. Sube tus cambios (`git push origin feature/nueva-funcionalidad`).
5. Abre un Pull Request.


## Contacto
Para más información o asistencia, por favor contacta con el desarrollador :

**Desarrollador**: Javier Morales Martos

https://github.com/Javier070
