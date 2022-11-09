FROM adoptopenjdk/openjdk11

CMD ["./gradlew" ,"build" ,"-x","check"]

ARG JAR_FILE=build/libs/batnam-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
