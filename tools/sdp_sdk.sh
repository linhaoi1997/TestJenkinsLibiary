#!/bin/bash
properties=src/main/resources/config.properties
echo " 
" >> $properties
#db
echo jdbc.url=jdbc:mysql://$host:$db_port >> $properties

#workspace
echo prophet.workspace=$sage_workspace >> $properties

#accessKey
echo prophet.accessKey=$accessKey  >> $properties

if [ -z $env_port ];then
    echo prophet.url=http://$host >> $properties
else
    echo prophet.url=https://$host:$env_port >> $properties
fi


#test environment

cat src/main/resources/config.properties