def initenv1(){

     def remote = [:]
     remote.name = 'docker02'
     remote.host = '172.27.2.52'
     remote.user = 'gaofei'
     remote.port = 22
     remote.password = '1987720sgf'
     remote.allowAnyHosts = true                                  
     sshCommand remote: remote, command: "cd /opt/public/prophetee-deploy-autoui;cat 380cdhruntime.ini"
                    
}
