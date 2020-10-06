#!/bin/bash
echo Making the jemafUtils jar file ....
mvn clean package

if [ $? != 0 ]; then
{
    echo "Error occurred in building the jar file "
    exit 1
} fi

echo Installing the jemafUtils jar file ....
mvn clean install

echo "10x4"
