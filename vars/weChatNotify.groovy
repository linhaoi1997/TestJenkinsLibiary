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
//import com.alibaba.fastjson.JSONObject;

//可以指定maven仓库
//@GrabResolver(name = 'aliyun', root = 'http://maven.aliyun.com/nexus/content/groups/public/')
//加载数据库连接驱动包
//@Grab('mysql:mysql-connector-java:5.1.25')
//@GrabConfig(systemClassLoader=true)



@NonCPS
def sendWechatAlarm(String webhookURL, String message) {
    String s="""
    {
    "msgtype": "text",
    "text": {
       "articles" : [
           {
               "content" : "${message}"
           }
        ]
    }
   }
   """
    HTTPBuilder http = new HTTPBuilder("${webhookURL}")
    def jsonSlurper = new groovy.json.JsonSlurper()
    def object1 = jsonSlurper.parseText(s)
    print object1


    http.post(path:'/',body:object1,requestContentType:URLENC){resp->
        assert resp.statusLine.statusCode == 200
    }

}




def call(String webhookURL, String message) {
    sendWechatAlarm(webhookURL, message)

}
