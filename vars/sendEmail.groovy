/**
 * Created by sungaofei on 20/2/8.
 */
@Grab(group = 'org.codehaus.groovy.modules.http-builder', module = 'http-builder', version = '0.7')

import groovyx.net.http.HTTPBuilder



import static groovyx.net.http.ContentType.*


import static groovyx.net.http.Method.*




@NonCPS
def call(){

    def fileContents = ""
    def passed = ""
    def reportURL = "http://auto.4paradigm.com/view/API/job/${JOB_NAME}/${BUILD_NUMBER}/allure/"

    def http = new HTTPBuilder('http://auto.4paradigm.com')
    //根据responsedata中的Content-Type header，调用json解析器处理responsedata
    http.get(path:"/view/API/job/${JOB_NAME}/${BUILD_NUMBER}/allure/widgets/summary.json"){resp,json->
        println resp.status
        passed = json.statistic.passed

    }

    println(passed)

    emailext body: """
<html>
  <style type="text/css">
  <!--
  ${fileContents}
  -->
  </style>
  <body>
  <div id="content">
  <h1>Summary</h1>
  <div id="sum2">
      <h2>Jenkins Build</h2>
      <ul>
      <li>Job URL : <a href='${BUILD_URL}'>${BUILD_URL}</a></li>
       <li>Build Result URL : <a href='${reportURL}'>${reportURL}</a></li>
      </ul>
  </div>
  <div id="sum0">
  <h2>GIT Branch</h2>
  <ul>

  </ul>
  </div>
  </div></body></html>
    """,charset:'UTF-8', mimeType:'text/html', subject: "${JOB_NAME} 测试结束", to: 'sungaofei@4paradigm.com'

}



