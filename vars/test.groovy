def call(){
     
    println("testgroovy")
    println(DEPLOY_ENV)
    def remote = [:]
    remote.name = DEPLOY_ENV_NAME
    remote.host = DEPLOY_ENV_HOST
    remote.user = DEPLOY_ENV_USER
    remote.port = DEPLOY_ENV_PORT
    remote.password = DEPLOY_ENV_PASSWORD
    remote.allowAnyHosts = DEPLOY_ENV_AllowAnyHosts  
    sshCommand remote: remote, command: DEPLOY_ENV_COMMOND
      

                    
}