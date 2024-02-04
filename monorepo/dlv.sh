#!/bin/bash
$@ &
PID=$!
kill -STOP $PID
echo $PID
# wait $PID
dlv attach $PID --listen=0.0.0.0:2345 --headless --log --api-version 2