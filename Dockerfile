# Etapa 1: Construcción de la aplicación
FROM maven:3.8.4-openjdk-17-slim AS build

# Crear directorio de trabajo para la aplicación
WORKDIR /app

# Copiar el archivo pom.xml y descargar las dependencias de Maven
COPY pom.xml .
RUN mvn dependency:resolve

# Copiar el resto de los archivos del proyecto y compilarlo
COPY src /app/src
RUN mvn clean test package

# Etapa 2: Configuración de Tomcat para ejecutar la aplicación
FROM tomcat:10.1.8-jdk17

# Crear un directorio para almacenar los datos de Derby
RUN mkdir /data

# Copiar el archivo WAR generado en la etapa de construcción
COPY --from=build /app/target/demo-java-web-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

# Configurar Derby como base de datos embebida y especificar el directorio de almacenamiento
ENV DERBY_HOME=/data

# Exponer el puerto en el que correrá Tomcat
EXPOSE 8080

# Comando para iniciar Tomcat
CMD ["catalina.sh", "run"]
