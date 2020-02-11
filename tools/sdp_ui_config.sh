#!/bin/bash
rm -rf allure-report
config=UIAutomation/src/main/resources/config.properties

echo "" >> $config

if [ -z $env_port ];then
    echo "base.url=$http_type://$host" >> $config
else
    echo "base.url=$http_type://$host:$env_port" >> $config
fi
echo "browser.version=68.0.3440.84" >> $config
echo "remote=http://m7-prod-ssd001:30000/wd/hub" >> $config

if [ -n "$sage_workspace" ];then
    echo "workspace=$sage_workspace" >> $config
fi
if [[ $host =~ autoui.4pd.io ]];then
    prophet_ns=$(echo $host |awk -F '[.]' '{print $2}')
    db_port_auto=$(ssh root@$host "k get svc -n $prophet_ns |grep mysql |head -n 1 |awk -F '[:/]' '{print \$2}'")
fi
if [ -z $db_port_auto ];then
	echo "jdbc.url=jdbc:mysql://$host:$db_port" >> $config
else
	echo "jdbc.url=jdbc:mysql://$host:$db_port_auto" >> $config
fi
if [[ $hive_Kerberos_open = CDHOPEN ]];then
	hdfs_url=""
fi
echo "jenkins_workspace=$WORKSPACE" >> $config
echo "
#test browser
browser=$browser
timeout=20000
hdfs.nameNodeUrl=$hdfs_url
hive_Kerberos_open=$hive_Kerberos_open
isOpenLdb=$isOpenLdb
" >> $config

cat UIAutomation/src/main/resources/config.properties


if [ $isOpenLdb = true ];then 	
	sed -i '/<exclude name="dango.cases.sdp.sqlPreprocess\"\/>/d'  UIAutomation/$xml.xml
fi


if [ $hive_Kerberos_open = CDHOPEN ];then
	sed -i '/<exclude name=\"dango.cases.sdp.hivecdhker\"\/>/d'  UIAutomation/$xml.xml
elif [ $hive_Kerberos_open = CDHCLOSE ];then
	sed -i '/<exclude name=\"dango.cases.sdp.hivecdh\"\/>/d'  UIAutomation/$xml.xml
elif [ $hive_Kerberos_open = C60OPEN ];then
	sed -i '/<exclude name="dango.cases.sdp.hivec60\"\/>/d'  UIAutomation/$xml.xml
elif [ $hive_Kerberos_open = C70OPEN ];then
	sed -i '/<exclude name="dango.cases.sdp.hivec70\"\/>/d'  UIAutomation/$xml.xml
elif [ $hive_Kerberos_open = HDP ];then
	sed -i '/<exclude name="dango.cases.sdp.hivehdp\"\/>/d'  UIAutomation/$xml.xml
elif [ $hive_Kerberos_open = LEAP ];then
	sed -i '/<exclude name="dango.cases.sdp.hiveleap\"\/>/d'  UIAutomation/$xml.xml

fi


cat UIAutomation/$xml.xml


 sed -i "s/thread-count=\".*\"/thread-count=\"$thread\"/g"  UIAutomation/$xml.xml
 cd UIAutomation
 if [ "$deletedata" = "true" -a "$importdata" = "true" ]; then 
	mvn clean test -Dmaven.test.failure.ignore=true -DsuiteXmlFile=dataCleanDeleteData.xml
    mvn clean test -DsuiteXmlFile=befortest.xml
    
 elif [ "$deletedata" = "false" -a "$importdata" = "true" ]; then 
   mvn clean test -DsuiteXmlFile=befortest.xml  
 
 elif [ "$deletedata" = "true" -a "$importdata" = "false" ]; then 
   mvn clean test -Dmaven.test.failure.ignore=true -DsuiteXmlFile=dataCleanDeleteData.xml
 else
    echo "您没有删除数据也没有引入数据哦"
 fi
 
 
