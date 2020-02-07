//global variable
def call(){
    sh """
        cd jacoco_report
        sh buildpipeline.sh 
       """
}

