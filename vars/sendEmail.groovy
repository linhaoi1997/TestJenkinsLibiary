/**
 * Created by sungaofei on 20/2/8.
 */
@Grab(group = 'org.codehaus.groovy.modules.http-builder', module = 'http-builder', version = '0.7')

import groovyx.net.http.HTTPBuilder



import static groovyx.net.http.ContentType.*


import static groovyx.net.http.Method.*





def call(){

    echo '111111111111'
    def body = ""
    def passed = ""

    def http = new HTTPBuilder('http://auto.4paradigm.com')
    //根据responsedata中的Content-Type header，调用json解析器处理responsedata
    http.get(path:'/view/API/job/sage-sdk-test/45/allure/widgets/summary.json'){resp,json->
        println resp.status
        passed = json.statistic.passed
    }

    emailext body: "测试结束。 通过：111", subject: 'sage-sdk 测试结束', to: 'sungaofei@4paradigm.com'

}



