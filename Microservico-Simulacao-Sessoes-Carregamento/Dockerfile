# Etapa 1: Usar uma imagem base com OpenJDK
FROM openjdk:21-jdk-slim

WORKDIR /app

# Etapa 2: Copiar o arquivo JAR para o contêiner
COPY target/Microservico-Simulacao-Sessoes-Carregamento-0.0.1-SNAPSHOT.jar /app/Microservico-Simulacao-Sessoes-Carregamento.jar

# Etapa 3: Expor a porta que o app usará
EXPOSE 8040

# Etapa 4: Definir as variáveis de ambiente (JAVA_OPTS por exemplo)
ENV JAVA_OPTS="-DEUREKA_SERVER_URL=http://eureka-server:8761/eureka -Dporta=8040"

# Etapa 5: Definir o comando para rodar o app com a variável JAVA_OPTS
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar Microservico-Simulacao-Sessoes-Carregamento.jar"]