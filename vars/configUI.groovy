import utils.*

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

   if [ -n "$ENV_WORKSPACE" ];then
     echo "workspace=$ENV_WORKSPACE" >> $config
   fi

   if [[ $HOST =~ autoui.4pd.io ]];then
      prophet_ns=$(echo $HOST |awk -F '[.]' '{print $2}')
      db_port_auto=$(ssh root@$HOST "k get svc -n $prophet_ns |grep mysql |head -n 1 |awk -F '[:/]' '{print \$2}'")
   fi
   
   echo "db_port_auto:"
   echo $db_port_auto
   
   if [ -z $db_port_auto ];then
    echo "jdbc.url=jdbc:mysql://$HOST:$DB_PORT" >> $config
   else
	echo "jdbc.url=jdbc:mysql://$HOST:$db_port_auto" >> $config
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


if [ $isOpenLdb = true ];then 	
	sed -i '/<exclude name="dango.cases.sdp.sqlPreprocess\"\/>/d'  testsuite/sdp/sdp+modelCenter.xml
fi


if [ $hive_Kerberos_open = CDHOPEN ];then
	sed -i '/<exclude name=\"dango.cases.sdp.hivecdhker\"\/>/d'  testsuite/sdp/sdp+modelCenter.xml
elif [ $hive_Kerberos_open = CDHCLOSE ];then
	sed -i '/<exclude name=\"dango.cases.sdp.hivecdh\"\/>/d'  testsuite/sdp/sdp+modelCenter.xml
elif [ $hive_Kerberos_open = C60OPEN ];then
	sed -i '/<exclude name="dango.cases.sdp.hivec60\"\/>/d'  testsuite/sdp/sdp+modelCenter.xml
elif [ $hive_Kerberos_open = C70OPEN ];then
	sed -i '/<exclude name="dango.cases.sdp.hivec70\"\/>/d'  testsuite/sdp/sdp+modelCenter.xml
elif [ $hive_Kerberos_open = HDP ];then
	sed -i '/<exclude name="dango.cases.sdp.hivehdp\"\/>/d'  testsuite/sdp/sdp+modelCenter.xml
elif [ $hive_Kerberos_open = LEAP ];then
	sed -i '/<exclude name="dango.cases.sdp.hiveleap\"\/>/d' testsuite/sdp/sdp+modelCenter.xml

fi


cat testsuite/sdp/sdp+modelCenter.xml


    """
}
