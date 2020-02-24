#!/bin/bash

DB_PASS=$1
cd api
sh run.sh $DB_PASS &> /dev/null
sleep 1.5s
cd ../frontend
sh runFrontend.sh
set +e 
kill $(lsof -t -i:3000)