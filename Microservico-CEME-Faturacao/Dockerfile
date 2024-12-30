# Etapa 1: Usar uma imagem base com OpenJDK
FROM openjdk:21-jdk-slim

WORKDIR /app
# Etapa 2: Copiar o arquivo JAR para o contêiner
COPY target/Microservico-CEME-Faturacao-0.0.1-SNAPSHOT.jar /app/Microservico-CEME-Faturacao.jar

# Etapa 3: Expor a porta que o app usará
EXPOSE 8050

# Etapa 4: Definir as variáveis de ambiente (JAVA_OPTS por exemplo)
ENV JAVA_OPTS="-DEUREKA_SERVER_URL=http://eureka-server:8761/eureka -Dporta=8050"
ENV SPRING_DATASOURCE_URL="jdbc:mysql://mysql-db:3306/db"
# Etapa 5: Definir o comando para rodar o app com a variável JAVA_OPTS
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar Microservico-CEME-Faturacao.jar"]