def call(){

    sh """
    echo "ADFSFSFSE"
    echo $APP_NAME
    /usr/bin/sshpass -p $JACOCO_SERVER_PASSWORD ssh -o StrictHostKeyChecking=no $JACOCO_SERVER_USER@$JACOCO_SERVER_HOST bash -s < jenkins-sharelib/tools/add_jacoco.sh $NAMESPACES ${DEPLOY_NAME}  ${AGENT_ENABLED} ${AGENT_INCLUDES} ${AGENT_PORT} ${AGENT_APPEND} ${APP_NAME}

    """

   
}
