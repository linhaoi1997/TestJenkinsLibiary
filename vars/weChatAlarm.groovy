def call(){

    sh """
    curl -o temp.xml  "$JENKINS_URL/job/$JKS_JOB_NAME/$JKS_BUILD_NUMBER/api/xml"
    build_status=`grep 'result'  temp.xml`
    build_status=${build_status##*<result>}
    build_status=${build_status%%</result>*}

    CONTEXT="Content-type:application/json"

    #WEBHOOK_URL="https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=0d839ec7-9a39-4375-a109-ef88cfb38b84"
    current_time=`date '+%Y-%m-%d %H:%M:%S'` 
    version=$(echo $BRANCH | awk -F "/" '{print $2}')

    #HTTP_CODE=`curl -l -H $CONTEXT -X POST -d '{"msgtype": "text", "text": {"content": "'"【$JKS_JOB_NAME】运行结束,构建结果：$build_status，链接：$JENKINS_URL/job/$JKS_JOB_NAME/$JKS_BUILD_NUMBER"'"}}' ${WEBHOOK_URL}`
    #HTTP_CODE=`curl -l -H $CONTEXT -X POST -d '{"msgtype": "text", "text": {"mentioned_list":["@all"]}}' ${WEBHOOK_URL}`
    HTTP_CODE=`curl -l -H $CONTEXT -X POST -d '{"msgtype": "markdown", "markdown": {"content": "<font color=\"info\">【'"$version"'自动化运行结果通知】</font>\n >运行结果：<font color=\"warning\">'"$build_status"'</font>\n >版本信息：'"$BRANCH"'\n >环境信息：'"$URL"'\n >[jenkins任务链接]('"$JENKINS_URL/job/$JKS_JOB_NAME/$JKS_BUILD_NUMBER"')\n >[allure报告链接]('"$JENKINS_URL/job/$JKS_JOB_NAME/$JKS_BUILD_NUMBER/allure/#/behaviors"')"}}' ${WEBHOOK_URL}`
    #curl -o temp.xml -X POST "http://auto.4paradigm.com/view/SDP/job/ui-auto-linkoop/5/api/xml"
   
    """

   
}