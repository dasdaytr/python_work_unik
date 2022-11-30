FROM openjdk:17
ENV ARTIFACT_NAME=SpringBoot-0.0.7.jar
ENV APP_HOME=/usr/app/test
WORKDIR $APP_HOME
COPY  service_docker/java_app $APP_HOME
ENV PORT_DB=5432
ENV PORT=8010
CMD ["java","-jar","SpringBoot-0.0.7.jar"]