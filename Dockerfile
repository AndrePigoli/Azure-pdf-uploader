# Imagem base com Java 17
FROM eclipse-temurin:17-jdk-alpine

# Diretório de trabalho dentro do container
WORKDIR /app

# Copia Maven Wrapper e pom.xml
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Copia código-fonte
COPY src ./src

# Dá permissão de execução ao mvnw
RUN chmod +x mvnw

# Build do projeto
RUN ./mvnw clean package -DskipTests

# Copia o JAR final
COPY target/*.jar app.jar

# Expõe porta 8080
EXPOSE 8080

# Comando para iniciar Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]
