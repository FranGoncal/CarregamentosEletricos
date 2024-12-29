# Etapa 1: Usar uma imagem base com OpenJDK
FROM openjdk:21-jdk-slim

WORKDIR /app

# Etapa 2: Copiar o arquivo JAR para o contêiner
COPY target/Microservico-Simulacao-Sessoes-Carregamento-0.0.1-SNAPSHOT.jar /app/Microservico-Simulacao-Sessoes-Carregamento.jar

# Etapa 3: Expor a porta que o app usará
EXPOSE 8040

# Etapa 4: Definir o comando para rodar o app
#ENTRYPOINT exec java $JAVA_OPTS -jar Microservico-FrontEnd-Server.jar
ENTRYPOINT ["java", "-jar", "Microservico-Simulacao-Sessoes-Carregamento.jar"]