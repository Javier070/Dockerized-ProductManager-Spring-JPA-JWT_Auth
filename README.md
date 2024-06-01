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

## Uso Básico

Una vez que la aplicación esté en funcionamiento, puedes acceder a los siguientes endpoints:

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

----
Para acceder a los endpoints protegidos, es necesario incluir un token JWT en el encabezado de la solicitud HTTP.

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
