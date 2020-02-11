def call(){
   
   sh """
    
   cd UIAutomation/UIAutomation
   config=src/main/resources/config.properties
   echo " " >> src/main/resources/config.properties
   
   if [ -z $ENV_PORT ];then
     echo "base.url=$HTTP_TYPE://$HOST" >> src/main/resources/config.properties
   else
     echo "base.url=$HTTP_TYPE://$HOST:$ENV_PORT" >> src/main/resources/config.properties
   fi

   echo "browser.version=68.0.3440.84" >> src/main/resources/config.properties
   echo "remote=http://m7-prod-ssd001:30000/wd/hub" >> src/main/resources/config.properties
   echo "jdbc.url=jdbc:mysql://$HOST:$DB_PORT" >> src/main/resources/config.properties

   if [ -n "$ENV_WORKSPACE" ];then
     echo "workspace=$ENV_WORKSPACE" >> src/main/resources/config.properties
   fi

   if [[ $hive_Kerberos_open = CDHOPEN ]];then
	  hdfs_url=""
   fi
   echo "jenkins_workspace=$WORKSPACE" >> src/main/resources/config.properties
   echo "
   browser=$browser
   timeout=20000
   hdfs.nameNodeUrl=$hdfs_url
   hive_Kerberos_open=$hive_Kerberos_open
   isOpenLdb=$isOpenLdb
   " >> src/main/resources/config.properties

   cat src/main/resources/config.properties


    """
}