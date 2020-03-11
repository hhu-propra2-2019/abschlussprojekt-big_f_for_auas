FROM gradle:jdk11 as BUILD
WORKDIR /home/gradle/src
COPY . .
RUN gradle bootJar

#---------------------------------------

FROM openjdk:11-jre-slim
WORKDIR /code
COPY --from=BUILD /home/gradle/src/build/libs/*.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]

#builden mit : docker build . -t TAG
#starten mit : docker run -p 8080:8080 -t test