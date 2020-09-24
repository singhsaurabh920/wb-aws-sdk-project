#!/bin/bash
pid=`ps -eaf | grep  aws-sdk.jar | grep -v "grep" | awk '{print $2}'`
if test -z "$pid" 
then
 echo "AWS sdk is not running"
else
 echo "AWS sdk previous Process ID: "$pid
 kill -9 $pid  
fi
nohup java -jar aws-sdk.jar </dev/null &>/dev/null &
echo "Starting....................."
sleep 2s
pid=`ps -eaf | grep  aws-sdk.jar | grep -v "grep" | awk '{print $2}'`
echo "AWS sdk current Process ID: "$pid

