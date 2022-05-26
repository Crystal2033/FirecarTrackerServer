FROM maven:3.6.0-jdk-11 AS build

COPY src /home/projects/tracker/src
COPY pom.xml /home/projects/tracker/
WORKDIR /home/projects/tracker/

RUN mvn -f /home/projects/tracker/pom.xml clean package

#
# Package stage
#

FROM openjdk:11-jre
COPY --from=build /home/projects/tracker/target/Tracker-1.0.0.jar /usr/local/lib/Tracker-1.0.0.jar

ENTRYPOINT ["java","-jar","/usr/local/lib/Tracker-1.0.0.jar"]
