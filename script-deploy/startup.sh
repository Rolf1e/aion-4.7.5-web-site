#!/bin/bash
git pull
mvn clean install
cd ../server/target/ || exit
pid = $(cat pid.txt)
if ps -p ${pid} >/dev/null; then
  echo "Web server is running"
else
  java-rmi -jar server-*-spring-boot.jar & >log.out 2>&1
  echo $! >pid.txt
fi
