def call(){

    sh """
    echo "ADFSFSFSE"
    echo $JACOCO_APP_NAME
    /usr/bin/sshpass -p $JACOCO_SERVER_PASSWORD ssh -o StrictHostKeyChecking=no $JACOCO_SERVER_USER@$JACOCO_SERVER_HOST bash -s < jenkins-sharelib/tools/add_jacoco.sh "$NAMESPACES" "$DEPLOY_NAME" "$JACOCO_AGENT_ENABLED" "$JACOCO_AGENT_INCLUDES" "$JACOCO_AGENT_PORT" "$JACOCO_AGENT_APPEND" "$JACOCO_APP_NAME"

    """

   
}
