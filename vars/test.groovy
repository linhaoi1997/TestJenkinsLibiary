import utils.*

def call(){
    sh """
    
    SSHPASS=/usr/bin/sshpass 
    echo $JACOCO_SERVER_PASSWORD
    
    pssh="$SSHPASS -p $JACOCO_SERVER_PASSWORD ssh -o StrictHostKeyChecking=no $JACOCO_SERVER_USER@$JACOCO_SERVER_HOST"
    
    $pssh bash -s < jenkins-sharelib/tools/add_jacoco.sh
    
    """
}