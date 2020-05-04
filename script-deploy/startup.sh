#!/bin/bash
git pull
cd ../server || exit
echo "Compiling Web server"
#mvn clean install || exit
cd target/ || exit
cp server-*-spring-boot.jar /c/Site\ Internet/deployment/
cd /c/Site\ Internet/deployment/ || exit
pid=`cat pid.txt`
if [ "$pid" == "start" ]; then 
	echo "Starting Web server /c/Site\ Internet/deployment/"
	nohup java-rmi -jar server-*-spring-boot.jar > log.out 2>&1 &
	echo $! > pid.txt
else 
	if ps -p ${pid} >/dev/null; then
	  echo "Web server is running /c/Site\ Internet/deployment/"
	else
	  echo "Starting Web Server /c/Site\ Internet/deployment/" 
	  nohup java-rmi -jar server-*-spring-boot.jar > log.out 2>&1 &
	  echo $! > pid.txt
	fi
fi
