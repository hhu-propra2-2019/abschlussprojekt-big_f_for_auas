FROM gradle:jdk11 as BUILD
WORKDIR /home/gradle/src
COPY . .
RUN gradle bootJar
RUN chmod +x wait-for-it.sh && mv wait-for-it.sh ./build/libs/wait-for-it.sh

#---------------------------------------

FROM openjdk:11-jre-slim
COPY --from=BUILD /home/gradle/src/build/libs/termine1.jar /home/gradle/src/build/libs/wait-for-it.sh /code/
EXPOSE 8080
CMD ["java", "-jar", "/code/termine1.jar"]