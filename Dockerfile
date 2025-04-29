# Usa uma imagem leve do Java 17
FROM eclipse-temurin:17-jdk-alpine

# Define o diretório de trabalho no container
WORKDIR /app

# Copia o JAR compilado para dentro do container
COPY Motogo/backend/target/backend-0.0.1-SNAPSHOT.jar app.jar

# Expõe a porta usada pela aplicação Spring Boot
EXPOSE 8080

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
