/**
 * Created by sungaofei on 20/2/8.
 */
@Grab(group = 'org.codehaus.groovy.modules.http-builder', module = 'http-builder', version = '0.7')

import groovyx.net.http.HTTPBuilder


import static groovyx.net.http.ContentType.*


import static groovyx.net.http.Method.*


@NonCPS
def call() {
    def fileContents = ""
    def passed = ""
    def failed = ""
    def skipped = ""
    def broken = ""
    def unknown = ""
    def total = ""

    def reportURL = "http://auto.4paradigm.com/view/API/job/${JOB_NAME}/${BUILD_NUMBER}/allure/"
    def blueOCeanURL = "http://auto.4paradigm.com/blue/organizations/jenkins/${JOB_NAME}/detail/${JOB_NAME}/${BUILD_NUMBER}/pipeline"

    def http = new HTTPBuilder('http://auto.4paradigm.com')
    //根据responsedata中的Content-Type header，调用json解析器处理responsedata
    http.get(path: "/view/API/job/${JOB_NAME}/${BUILD_NUMBER}/allure/widgets/summary.json") { resp, json ->
        println resp.status
        passed = json.statistic.passed
        failed = json.statistic.failed
        skipped = json.statistic.skipped
        broken = json.statistic.broken
        unknown = json.statistic.unknown
        total = json.statistic.total

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
      <li>Job 地址 : <a href='${BUILD_URL}'>${BUILD_URL}</a></li>
       <li>测试报告地址 : <a href='${reportURL}'>${reportURL}</a></li>
       <li>Pipeline 流程地址 : <a href='${blueOCeanURL}'>${blueOCeanURL}</a></li>
      </ul>

      <h2>测试结果汇总</h2>
      <ul>
      <li>用例总数 : ${total}</li>
      <li>pass数量 : ${passed}</li>
       <li>failed数量 :${failed} </li>
       <li>skip数量 : ${skipped}</li>
       <li>broken数量 : ${broken}</li>
      </ul>
  </div>
  <div id="sum0">
  <h2>GIT Branch</h2>
  <ul>

  </ul>
  </div>
  </div></body></html>
    """, charset: 'UTF-8', mimeType: 'text/html', subject: "${JOB_NAME} 测试结束", to: 'sungaofei@4paradigm.com'

}



