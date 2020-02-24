#!/bin/bash
export DB_PASSWORD=$1

JAR=api-1.0-SNAPSHOT.jar

java -jar target/$JAR server ./config.yml