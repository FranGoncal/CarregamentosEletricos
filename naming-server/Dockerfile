
# Etapa 1: Usar uma imagem base com OpenJDK
FROM openjdk:21-jdk-slim

WORKDIR /app
# Etapa 2: Copiar o arquivo JAR para o contêiner
COPY target/naming-server-0.0.1-SNAPSHOT.jar /app/naming-server.jar

# Etapa 3: Expor a porta que o app usará
EXPOSE 8761

# Etapa 4: Definir o comando para rodar o app
#ENTRYPOINT ["java", "-jar", "/app.jar"]
ENTRYPOINT ["java", "-jar", "naming-server.jar"]