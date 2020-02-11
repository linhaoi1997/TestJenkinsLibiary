def call(){
   
   sh """
    
   cd UIAutomation/UIAutomation
   config=src/main/resources/config.properties
   echo " " >> $config
   
   if [ -z $ENV_PORT ];then
     echo "base.url=$HTTP_TYPE://$HOST" >> $config
   else
     echo "base.url=$HTTP_TYPE://$HOST:$ENV_PORT" >> $config
   fi

   echo "browser.version=68.0.3440.84" >> $config
   echo "remote=http://m7-prod-ssd001:30000/wd/hub" >> $config
   echo "jdbc.url=jdbc:mysql://$HOST:$DB_PORT" >> $config

   if [ -n "$ENV_WORKSPACE" ];then
     echo "workspace=$ENV_WORKSPACE" >> $config
   fi

   if [[ $hive_Kerberos_open = CDHOPEN ]];then
	  hdfs_url=""
   fi
   echo "jenkins_workspace=$WORKSPACE" >> $config
   echo "
   browser=$browser
   timeout=20000
   hdfs.nameNodeUrl=$hdfs_url
   hive_Kerberos_open=$hive_Kerberos_open
   isOpenLdb=$isOpenLdb
   " >> $config

   cat src/main/resources/config.properties


    """
}
