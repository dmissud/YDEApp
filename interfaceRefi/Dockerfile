FROM openjdk:8-alpine
COPY target/*.jar YDEBatch.jar
RUN mkdir -p /var/lib/ydeapp
ENTRYPOINT ["java","-jar","YDEBatch.jar"]
