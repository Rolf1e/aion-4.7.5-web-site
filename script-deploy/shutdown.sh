#!/bin/bash
cd /c/Site\ Internet/deployment
if ps -p `cat pid.txt` > /dev/null
then
  pid=`cat pid.txt`
  kill ${pid}
I  echo "Stopping Web server in /c/Site\ Internet/deployment"
else
  echo "Web Server is not running."
fi
exit 0
