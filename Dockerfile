FROM eclipse-temurin:17.0.13_11-jre-ubi9-minimal

WORKDIR /app

COPY target/easy-funds.jar /app/easy-funds.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/easy-funds.jar"]
