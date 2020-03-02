package utils

import groovy.grape.Grape

/**
 * Created by sungaofei on 20/3/1.
 */

@Grab(group = 'org.codehaus.groovy.modules.http-builder', module = 'http-builder', version = '0.7')
import groovyx.net.http.HTTPBuilder
import static groovyx.net.http.ContentType.*
import static groovyx.net.http.Method.*
import groovy.transform.Field
import groovy.sql.Sql

//可以指定maven仓库
//@GrabResolver(name = 'aliyun', root = 'http://maven.aliyun.com/nexus/content/groups/public/')
//加载数据库连接驱动包
//@Grab('mysql:mysql-connector-java:5.1.25')
//@GrabConfig(systemClassLoader=true)

//global variable
@Field jenkinsURL = "http://auto.4paradigm.com"


def call(String version) {
//    ClassLoader classLoader = new GroovyClassLoader()
//    Map[] grapez = [[group : 'mysql', module : 'mysql-connector-java', version : '5.1.25']]
//    Grape.grab(classLoader: classLoader, grapez)

    def reportURL = ""
    if (env.BRANCH_NAME != "" && env.BRANCH_NAME != null) {
        reportURL = "/view/API/job/${jobName}/job/${env.BRANCH_NAME}/${BUILD_NUMBER}/allure/"
    } else {
        reportURL = "/view/API/job/${JOB_NAME}/${BUILD_NUMBER}/allure/"
    }


    int passed
    int failed
    int skipped
    int broken
    int unknown
    int total
    HTTPBuilder http = new HTTPBuilder(jenkinsURL)
    //根据responsedata中的Content-Type header，调用json解析器处理responsedata
    http.get(path: "${reportURL}widgets/summary.json") { resp, json ->
        println resp.status
        passed = Integer.parseInt((String) json.statistic.passed)
        failed = Integer.parseInt((String) json.statistic.failed)
        skipped = Integer.parseInt((String) json.statistic.skipped)
        broken = Integer.parseInt((String) json.statistic.broken)
        unknown = Integer.parseInt((String) json.statistic.unknown)
        total = Integer.parseInt((String) json.statistic.total)
    }
    //创建sql实例
//    Class.forName("com.mysql.jdbc.Driver")
//    url = 'jdbc:mysql://m7-qa-test03:3306/sage_sdk?useUnicode=true&characterEncoding=utf8'
//    driver = 'com.mysql.jdbc.Driver'
//    username = 'root'
//    passwd = 'root'
    getDatabaseConnection(type: 'GLOBAL') {
        def sqlString = "INSERT INTO func_test (name, version, total, passed, unknown, skipped, failed, broken, create_time) VALUES ('${JOB_NAME}', '${version}', " +
                "${total}, ${passed}, ${unknown}, ${skipped}, ${failed}, ${broken}, NOW())"
//        def params = ['test', 11]
        sql sql: sqlString
    }
//    def s = Sql.withInstance(url, username, passwd, driver) { sql ->
//        sql.execute "INSERT INTO func_test (name, version, total, passed, unknown, skipped, failed, broken, create_time) VALUES ('${JOB_NAME}', '${version}', " +
//                "${total}, ${passed}, ${unknown}, ${skipped}, ${failed}, ${broken}, NOW())"
//    }
}