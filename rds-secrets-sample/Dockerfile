FROM amazoncorretto:11-alpine3.14
ENV SECRET=''
ENV DB_HOST=''
ENV DB_SCHEMA=''
COPY target/rdssecrets-0.0.1-SNAPSHOT.jar  rdssecrets-0.0.1-SNAPSHOT.jar 
ENTRYPOINT ["java","-jar","/rdssecrets-0.0.1-SNAPSHOT.jar"]