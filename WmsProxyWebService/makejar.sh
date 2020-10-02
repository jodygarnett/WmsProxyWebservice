#!/bin/bash

echo Making the wmsproxy war file ....

echo Changing for build file ....

cp -f  src/main/webapp/WEB-INF/classes/log4j.properties         src/main/webapp/WEB-INF/classes/log4j.properties.bck
cp -f  src/main/webapp/WEB-INF/classes/log4j-build.properties   src/main/webapp/WEB-INF/classes/log4j.properties

mvn clean package

if [ $? != 0 ]; then
{
    echo "Error occurred in building the jar file "
} fi

echo Restoring from backup file ....

mv -f src/main/webapp/WEB-INF/classes/log4j.properties.bck        src/main/webapp/WEB-INF/classes/log4j.properties

echo "10x4"


