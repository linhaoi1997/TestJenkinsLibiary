/**
 * Created by sungaofei on 20/2/8.
 */
@Grab(group = 'org.codehaus.groovy.modules.http-builder', module = 'http-builder', version = '0.7')

import groovyx.net.http.HTTPBuilder


import static groovyx.net.http.ContentType.*


import static groovyx.net.http.Method.*
import groovy.transform.Field

//global variable
@Field jenkinsURL = "http://auto.4paradigm.com"
@Field failed = "FAILED"
@Field success = "SUCCESS"
@Field inProgress = "IN_PROGRESS"
@Field abort = "ABORTED"

@NonCPS
def String checkJobStatus() {
    def url = "/view/API/job/${JOB_NAME}/${BUILD_NUMBER}/wfapi/describe"
    HTTPBuilder http = new HTTPBuilder(jenkinsURL)
    String status = "SUCCESS"
    http.get(path: url) { resp, json ->
        if (resp.status != 200) {
            throw new RuntimeException("请求 ${url} 返回 ${resp.status} ")
        }

        if (json.status == abort){
            status = abort
        }else{
            List stages = json.stages
            JsonSlurper jsonSlurper = new JsonSlurper()

            for (int i = 0; i < stages.size(); i++) {
                def stageStatus = jsonSlurper.parseText(stages.get(i))
                if (stageStatus != success && stageStatus != inProgress){

                }
            }
        }
    }

    return status;

}


@NonCPS
def call(String to) {
    println("邮件列表：${to}")

    def sendSuccess = {
        def reportURL = "${jenkinsURL}/view/API/job/${JOB_NAME}/${BUILD_NUMBER}/allure/"
        def blueOCeanURL = "${jenkinsURL}/blue/organizations/jenkins/${JOB_NAME}/detail/${JOB_NAME}/${BUILD_NUMBER}/pipeline"

        def fileContents = ""
        def passed = ""
        def failed = ""
        def skipped = ""
        def broken = ""
        def unknown = ""
        def total = ""
        HTTPBuilder http = new HTTPBuilder('http://auto.4paradigm.com')
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
  </div></body></html>
    """, mimeType: 'text/html', subject: "${JOB_NAME} 测试结束", to: to

    }

    def send = { String subject ->
        emailext body: """
<html>
  <style type="text/css">
  <!--
  -->
  </style>
  <body>
  <div id="sum2">
      <h2>Jenkins Build</h2>
      <ul>
      <li>Job 地址 : <a href='${BUILD_URL}'>${BUILD_URL}</a></li>
      </ul>
  </div>
  </div></body></html>
    """, mimeType: 'text/html', subject: subject, to: to
    }

    String status = checkJobStatus()
    println("当前job 的运行状态为： ${status}")
    switch (status) {
        case ["SUCCESS", "UNSTABLE"]:
            sendSuccess()
            break
        case "FAILED":
            send("Job运行失败")
            break
        case "ABORTED":
            send("Job在运行中被取消")
            break
        default:
            send("Job运行结束")
    }

}



