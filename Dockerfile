FROM openjdk:8-jdk-alpine

VOLUME /mnt/data
USER root

ENV JAVA_HEAP_OPTS="-Xms2g -Xmx4g"
ENV JAVA_OPTS=""

COPY target /opt/target
WORKDIR /opt/target
RUN find -type f -name "*.jar" | xargs -I{} mv {} app.jar
RUN mkdir -p logs/gc

RUN echo -e "https://mirrors.aliyun.com/alpine/latest-stable/main\nhttps://mirrors.aliyun.com/alpine/latest-stable/community" >/etc/apk/repositories \
  && apk update -f && apk --no-cache add -f \
  curl tzdata \
  && rm -rf /var/cache/apk/* \
  && cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo "Asia/Shanghai" > /etc/timezone && apk del tzdata

ENTRYPOINT exec java $JAVA_HEAP_OPTS $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -XX:+UseConcMarkSweepGC -XX:CMSInitiatingOccupancyFraction=68 -jar app.jar