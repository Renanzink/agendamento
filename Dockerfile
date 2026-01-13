# Estágio de Build
FROM maven:3.9.6-eclipse-temurin-21 AS build
# Define o diretório de trabalho
WORKDIR /app
# Copia todos os arquivos do projeto (incluindo o pom.xml)
COPY . .
# Executa o build
RUN mvn clean package -DskipTests

# Estágio de Execução
FROM eclipse-temurin:21-jre
WORKDIR /app
# Copia o jar gerado no estágio anterior
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]