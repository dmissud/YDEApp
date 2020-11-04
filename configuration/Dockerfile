FROM openjdk:8-alpine
COPY target/*.jar YDEApp.jar
RUN mkdir -p /var/lib/ydeapp
EXPOSE 9090
ENTRYPOINT ["java","-jar","YDEApp.jar"]
