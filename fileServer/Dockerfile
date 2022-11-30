FROM gradle:7.5.1-jdk17-alpine AS BUILD

ENV APP_HOME=/usr/app/test
WORKDIR $APP_HOME
COPY . $APP_HOME
WORKDIR /app
ENV GRADLE_HOME=/app/gradle-7.5.1
ENV PATH=$PATH:$GRADLE_HOME/bin
WORKDIR $APP_HOME
RUN ./gradlew build



FROM openjdk:17
ENV ARTIFACT_NAME=SpringBoot-0.0.8.jar
ENV APP_HOME=/usr/app/test
WORKDIR $APP_HOME
COPY --from=BUILD $APP_HOME/build/libs/$ARTIFACT_NAME .
ENV PORT_DB=5432
ENV PORT=8004
CMD ["java","-jar","SpringBoot-0.0.8.jar"]