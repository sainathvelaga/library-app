FROM maven:3.9.9-eclipse-temurin-17 AS builder
WORKDIR /app
COPY . /app
RUN mvn clean package -DskipTests
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
ENV spring.datasource.url=jdbc:mysql://localhost:3306/reactlibrarydatabase?useSSL=false&useUnicode=yes&characterEncoding=UTF-8&allowPublicKeyRetrieval=true&serverTimezone=UTC
ENV spring.datasource.username=lib_user
ENV spring.datasource.password=LibApp@1
COPY --from=builder /app/target/library-app-be-0.0.1-SNAPSHOT.jar /app/library-app-be-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "library-app-be-0.0.1-SNAPSHOT.jar"]