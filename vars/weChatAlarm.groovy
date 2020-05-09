import groovy.grape.Grape

@Grab(group = 'org.codehaus.groovy.modules.http-builder', module = 'http-builder', version = '0.7')
@Grab(group = 'org.jsoup', module = 'jsoup', version = '1.10.3')
import org.jsoup.Jsoup
import groovyx.net.http.HTTPBuilder


import static groovyx.net.http.ContentType.*
import static groovyx.net.http.Method.*
import groovy.transform.Field

import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.URL
import java.net.URLConnection

//可以指定maven仓库
//@GrabResolver(name = 'aliyun', root = 'http://maven.aliyun.com/nexus/content/groups/public/')
//加载数据库连接驱动包
//@Grab('mysql:mysql-connector-java:5.1.25')
//@GrabConfig(systemClassLoader=true)

//global variable
@Field jenkinsURL = "http://auto.4paradigm.com"

@Field int passed
@Field int failed
@Field int skipped
@Field int broken
@Field int unknown
@Field int total
@Field String t1
@Field Map<String, Map<String, Integer>> map = new HashMap<>()

@NonCPS
def sendWechatAlarm() {
    def reportURL = ""
    reportURL = "/view/SDP/job/${JKS_JOB_NAME}/${JKS_BUILD_NUMBER}/allure/"
   
    HTTPBuilder http = new HTTPBuilder(jenkinsURL)
    
    http.get(path: "${reportURL}widgets/summary.json") { resp, json ->
        println resp.status
        println "testA"
        passed = Integer.parseInt((String) json.statistic.passed)
        failed = Integer.parseInt((String) json.statistic.failed)
        skipped = Integer.parseInt((String) json.statistic.skipped)
        broken = Integer.parseInt((String) json.statistic.broken)
        unknown = Integer.parseInt((String) json.statistic.unknown)
        total = Integer.parseInt((String) json.statistic.total)
        println passed
        println total
    }
    
    // webURL="https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=c916b757-a1a2-416d-bf63-10fb8cf769e5"
    HTTPBuilder http1 = new HTTPBuilder("${WEBHOOK_URL}")
    
    http1.request( POST, JSON ) { req ->
	    
	    t1="\'【${version}自动化运行结果通知】\n >环境信息：${SAGE_URL}\'"
	    println t1
	    
	    body1= [
	            content: t1
	        
	        ]
	        
	    body = [
	    msgtype : 'markdown',
	    
	    markdown : body1

	    ]
	
	response.success = { resp, json ->
		// TODO process json data
		println resp.status
		println "testB"
	}
}

}
def sendPostRequest(urlString, paramString) {
    def url = new URL(urlString)
    def conn = url.openConnection()
    conn.setDoOutput(true)
    def writer = new OutputStreamWriter(conn.getOutputStream())

    writer.write(paramString)
    writer.flush()
    String line
    def reader = new BufferedReader(new     InputStreamReader(conn.getInputStream()))
    while ((line = reader.readLine()) != null) {
      println line
    }
    writer.close()
    reader.close()
}




def call(String coverage = null, String version="release/3.8.2") {
    sendWechatAlarm()
    //sendPostRequest("https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=c916b757-a1a2-416d-bf63-10fb8cf769e5", "msgtype=markdown&markdown={\"content\": \"test2\"}")

}


