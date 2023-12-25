# Utilizar una imagen base de OpenJDK
FROM maven:3.8.4-openjdk-17 AS build

# Copiar el código fuente a la imagen
COPY src /home/app/src
COPY pom.xml /home/app

# Construir la aplicación
RUN mvn -f /home/app/pom.xml clean package -DskipTests

# Utilizar otra imagen base para el contenedor final
FROM openjdk:17

# Argumento para el puerto (opcional)
ARG PORT=8080
EXPOSE $PORT

# Copiar el JAR desde la etapa de construcción
COPY --from=build /home/app/target/tp_spring-0.0.1.jar /usr/local/lib/tp_spring-0.0.1.jar

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "/usr/local/lib/tp_spring-0.0.1.jar"]