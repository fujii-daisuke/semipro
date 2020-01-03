FROM openjdk:jdk-alpine
VOLUME /tmp
RUN mkdir /app
WORKDIR /app
ENV JAVA_OPTS=""
ENV JAR_TARGET "semipro-web-0.0.1-SNAPSHOT.war"
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar build/libs/semipro-web-0.0.1-SNAPSHOT.war" ]