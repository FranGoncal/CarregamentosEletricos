
# Etapa 1: Usar uma imagem base com OpenJDK
FROM openjdk:21-jdk-slim

WORKDIR /app
# Etapa 2: Copiar o arquivo JAR para o contêiner
COPY target/OPC-0.0.1-SNAPSHOT.jar /app/OPC.jar

# Etapa 3: Expor a porta que o app usará
EXPOSE 8020

# Etapa 4: Definir o comando para rodar o app
ENTRYPOINT ["java", "-jar", "OPC.jar"]