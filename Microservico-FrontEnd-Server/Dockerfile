# Etapa 1: Usar uma imagem base com OpenJDK
FROM openjdk:21-jdk-slim

WORKDIR /app

# Etapa 2: Copiar o arquivo JAR para o contêiner
COPY target/Microservico-FrontEnd-Server-0.0.1-SNAPSHOT.jar /app/Microservico-FrontEnd-Server.jar

# Etapa 3: Expor a porta que o app usará
EXPOSE 8080

# Etapa 4: Definir as variáveis de ambiente (JAVA_OPTS por exemplo)
ENV JAVA_OPTS="-DEUREKA_SERVER_URL=http://eureka-server:8761/eureka -Dporta=8080"

# Etapa 5: Definir o comando para rodar o app com a variável JAVA_OPTS
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar Microservico-FrontEnd-Server.jar"]