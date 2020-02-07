def call(){

   def jacocoServer = [:]
   jacocoServer.name = JACOCO_SERVER_NAME
   jacocoServer.host = JACOCO_SERVER_HOST
   jacocoServer.user = JACOCO_SERVER_USER
   jacocoServer.password = JACOCO_SERVER_PASSWORD
   jacocoServer.allowAnyHosts = JACOCO_SERVER_ALLOWANYHOSTS
   sshCommand remote: jacocoServer, command: JACOCO_SERVER_COMMOND
   
}
