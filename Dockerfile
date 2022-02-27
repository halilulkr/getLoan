FROM openjdk:11
WORKDIR /app
ADD target/getLoan-0.0.1-SNAPSHOT.jar getLoan-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","getLoan-0.0.1-SNAPSHOT.jar"]