
def call(){
  
    String str = "this is a test"
    echo str
    sh '''
    
    echo ${JKS_JOB_NAME}
    
    '''
}
