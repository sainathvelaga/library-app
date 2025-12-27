FROM openjdk:17-alpine as builder
WORKDIR /app
RUN mvn clean install
COPY . /app
FROM openjdk:17-alpine
WORKDIR /app
ENV spring.datasource.url=jdbc:mysql://localhost:3306/reactlibrarydatabase?useSSL=false&useUnicode=yes&characterEncoding=UTF-8&allowPublicKeyRetrieval=true&serverTimezone=UTC
ENV spring.datasource.username=lib_user
ENV spring.datasource.password=LibApp@1
COPY --from=builder /app/target/library-app.jar /app/library-app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "library-app.jar"]