#!/bin/bash

echo Making the wmsproxy war file ....

mvn clean package

if [ $? != 0 ]; then
{
    echo "Error occurred in building the jar file "
} fi

echo "10x4"


