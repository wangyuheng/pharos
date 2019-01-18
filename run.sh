#! /bin/bash
echo -n "stop existing compose "
docker-compose down

echo -n "build jar"
mvn clean install -Dmaven.test.skip

echo -n "start compose"
docker-compose up