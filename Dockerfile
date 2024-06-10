#IMG API REST

#FROM: Es la instrucción que se utiliza para crear una imagen a partir de una imagen base.
#FROM [nombre_imagen]:[etiqueta]
#especifica la imagen base de del JDK.
FROM eclipse-temurin:21


#COPY copiar archivos del local al -> contenedor que estoy  construyendo
#COPY copiar el archivo jar y ponerlo en la carpeta raiz del contenedor
#COPY [origen] [destino]
COPY  target/com.jm.tfg-0.0.1-SNAPSHOT.jar tfg.jar


#java: Este es el comando para iniciar la máquina virtual Java (JVM).
#-jar: Esta es una opción que le dice a la JVM que quieres ejecutar un archivo JAR.
#tfg.jar: Este es el nombre del archivo JAR que quieres ejecutar.

#ENTRYPOINT permite configurar un contenedor para que se ejecute como un ejecutable.
ENTRYPOINT ["java", "-jar", "tfg.jar"]


#----------------------------------------------
#Construcción y Ejecución del Contenedor
#
# Construir la imagen: docker build -t java-tfg .
# Construir la imagen: docker build java-tfg


# Ejecutar la imagen: docker run -p 8080:8080 -t java-tfg
#El primer 8080 (antes de los dos puntos) es el puerto de tu máquina local.
#El segundo 8080 (después de los dos puntos) es el puerto dentro del contenedor de Docker.
