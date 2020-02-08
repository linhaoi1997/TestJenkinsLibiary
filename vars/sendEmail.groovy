/**
 * Created by sungaofei on 20/2/8.
 */
@Grab(group = 'org.codehaus.groovy.modules.http-builder', module = 'http-builder', version = '0.7')

import groovyx.net.http.HTTPBuilder



import static groovyx.net.http.ContentType.*


import static groovyx.net.http.Method.*





def call(){

    def body = ""
    def passed = ""

    def http = new HTTPBuilder('http://auto.4paradigm.com')
    //根据responsedata中的Content-Type header，调用json解析器处理responsedata
    http.get(path:'/view/API/job/sage-sdk-test/45/allure/widgets/summary.json'){resp,json->
        println resp.status
        passed = json.statistic.passed
    }

    println(passed)

    mail bcc: '', body: '231', cc: '', from: 'sungaofei@4paradigm.com', replyTo: '', subject: 'sage sdk 测试结束', to: 'sungaofei@4paradigm.com'
//    emailext body: "123。 passed：${passed}", subject: '123123', to: 'sungaofei@4paradigm.com'

}



