FROM openjdk:8-jdk-alpine
COPY target/test-1.0.jar app.jar
EXPOSE 8085
ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar

