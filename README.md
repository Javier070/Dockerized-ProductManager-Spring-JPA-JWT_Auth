# com.jm.tfg


## Descripción

Esta es una aplicación web diseñada para la gestión de productos y categorías. La aplicación permite a los usuarios autenticarse y realizar operaciones.

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

- **Java 11** o superior
- **Maven**
- **MySQL**
- **Docker**


## Instalación

1. **Clonar el repositorio**:
   ```bash
   git clone <URL_DEL_REPOSITORIO>
   ```

2. **Navegar al directorio del proyecto**:
   ```bash
   cd <NOMBRE_DEL_DIRECTORIO>
   ```

3. **Configurar la base de datos**:
    - Crear una base de datos MySQL.
    - Actualizar el archivo `application.properties` con las credenciales de la base de datos.

4. **Compilar y empaquetar la aplicación**:
   ```bash
   mvn clean package
   ```

5. **Ejecutar la aplicación**:
   ```bash
   java -jar target/<NOMBRE_DEL_JAR>.jar
   ```
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

1. **Descargar la Imagen Docker**

   Primero, asegúrate de tener Docker instalado en tu sistema. Luego, descarga la imagen de Docker para la aplicación.

   ```bash
   docker pull ghcr.io/javier070/java-tfg:1.0.6
   ```
2. **Ejecutar el Contenedor**

    A continuación, ejecuta el contenedor utilizando el comando docker run. 

    Este comando configura y ejecuta el contenedor de la aplicación
    con las variables de entorno necesarias para conectar con la base de datos MySQL.

    ```bash
    docker run --name tfg-backend50 --network comjmtfg_default -p 8080:8080 -e SPRING_DATASOURCE_URL=jdbc:mysql://mysql-tfg:3306/tfg -e SPRING_DATASOURCE_USERNAME=root -e SPRING_DATASOURCE_PASSWORD=<PASSWORD> ghcr.io/javier070/java-tfg:1.0.6
   ```
    Asegúrate de reemplazar <PASSWORD> con la contraseña real del usuario root de tu base de datos MySQL.



3. **Verificar la Ejecución**

   Una vez ejecutado el comando anterior, la aplicación debería estar corriendo en http://localhost5500/Inicio/login/login.html   
        Puedes verificar accediendo a esta URL en tu navegador.
    


----

## Contribución

Si deseas contribuir a este proyecto, por favor sigue estos pasos:

1. Haz un fork del proyecto.
2. Crea una nueva rama (`git checkout -b feature/nueva-funcionalidad`).
3. Realiza tus cambios y haz commit (`git commit -m 'Añadir nueva funcionalidad'`).
4. Sube tus cambios (`git push origin feature/nueva-funcionalidad`).
5. Abre un Pull Request.


## Contacto
Para más información o asistencia, por favor contacta con el desarrollador:

**Desarrollador**: Javier Morales Martos

https://github.com/Javier070
