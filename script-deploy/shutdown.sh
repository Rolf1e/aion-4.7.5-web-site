#!/bin/bash
if [ -e pid.txt ]
then
  pid=`cat pid.txt`
  kill ${pid}
  echo "Web server stops"
else
  echo "Web Server is not running."
fi
exit 0
