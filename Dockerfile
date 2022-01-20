FROM amazoncorretto:11
MAINTAINER Saurabh Singh
ARG Port=9092
ENV Profile=dev
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} /usr/app/app.jar
WORKDIR /usr/app
RUN echo $Port
RUN echo $Profile
EXPOSE ${Port}
ENTRYPOINT ["java","-Xms500m","-Xmx1g","-jar","-Dspring.profiles.active=${Profile}","app.jar"]