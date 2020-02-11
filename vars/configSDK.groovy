def call(){

  sh """
    cd runtime
    cat ./src/main/resources/config.properties
    
    echo a >> ./src/main/resources/config.properties
    echo jdbc.url=jdbc:mysql://$HOST:$DB_PORT >> ./src/main/resources/config.properties
    echo prophet.workspace=$WORKSPACE >> ./src/main/resources/config.properties
    echo prophet.accessKey=$ACCESSKEY  >> ./src/main/resources/config.properties

    if [ -z $ENV_PORT ];then
       echo prophet.url=http://$HOST >> ./src/main/resources/config.properties
    else
       echo prophet.url=https://$HOST:$ENV_PORT >> ./src/main/resources/config.properties
    fi

 
    sed -i "s/thread-count=\".*\"/thread-count=\"$THREAD\"/g" sdp.xml
    
    cat ./src/main/resources/config.properties

    """
}
