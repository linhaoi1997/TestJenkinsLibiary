import utils.*

def call(){
    sh """
    
    echo $JACOCO_SERVER_PASSWORD
    echo "AABCC"
    
    /usr/bin/sshpass -p $JACOCO_SERVER_PASSWORD ssh -o StrictHostKeyChecking=no $JACOCO_SERVER_USER@$JACOCO_SERVER_HOST bash -s < jenkins-sharelib/tools/add_jacoco.sh
    
    """
}