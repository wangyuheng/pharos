FROM openjdk:8-jdk-alpine

VOLUME /mnt/data
USER root

ENV TZ=Asia/Shanghai
ENV JAVA_HEAP_OPTS="-Xms2g -Xmx4g"
ENV JAVA_OPTS=""

COPY target /opt/target
WORKDIR /opt/target

RUN find -type f -name "*.jar" | xargs -I{} mv {} app.jar
RUN mkdir -p logs/gc

RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

ENTRYPOINT exec java $JAVA_HEAP_OPTS $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -XX:+UseConcMarkSweepGC -XX:CMSInitiatingOccupancyFraction=68 -jar app.jar