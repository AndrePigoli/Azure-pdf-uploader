# Etapa 1: Build do projeto com Maven
FROM maven:3.9.2-eclipse-temurin-17 AS build

# Define diretório de trabalho dentro do container
WORKDIR /app

# Copia arquivos de configuração do Maven e do projeto
COPY pom.xml .
COPY src ./src

# Build do projeto e criação do jar
RUN mvn clean package -DskipTests

# Etapa 2: Criação da imagem final com apenas o JAR
FROM eclipse-temurin:17-jdk-alpine

# Diretório de trabalho para o container
WORKDIR /app

# Copia o JAR gerado na etapa de build
COPY --from=build /app/target/blob-storage-service-0.0.1-SNAPSHOT.jar app.jar

# Expor a porta padrão do Spring Boot
EXPOSE 8080

# Comando para rodar o Spring Boot
ENTRYPOINT ["java","-jar","app.jar"]
