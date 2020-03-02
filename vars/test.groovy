package utils

import groovy.grape.Grape

/**
 * Created by sungaofei on 20/3/1.
 */

@Grab(group = 'org.codehaus.groovy.modules.http-builder', module = 'http-builder', version = '0.7')
import groovyx.net.http.HTTPBuilder

import java.sql.DriverManager
import java.sql.ResultSet

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

@Field int passed
@Field int failed
@Field int skipped
@Field int broken
@Field int unknown
@Field int total

@NonCPS
def getResultFromAllure(){
    def reportURL = ""
    if (env.BRANCH_NAME != "" && env.BRANCH_NAME != null) {
        reportURL = "/view/API/job/${jobName}/job/${env.BRANCH_NAME}/${BUILD_NUMBER}/allure/"
    } else {
        reportURL = "/view/API/job/${JOB_NAME}/${BUILD_NUMBER}/allure/"
    }



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
}


def call() {
    def version = "release/3.8.2"
    getResultFromAllure()
    println System.getProperty("java.ext.dirs")
    sh "ls -l ${System.getProperty("java.ext.dirs")}"
    this.class.classLoader.addURL(new URL("file://root/mysql-connector-java-8.0.13.jar"))
    Class.forName("com.mysql.jdbc.Driver", true, this.class.classLoader)
    def conn = DriverManager.getConnection("jdbc:mysql://m7-qa-test03:3306/sage_sdk", "root", "root")
    stmt = conn.createStatement();
    String sql;
    sql = "SELECT * from func_test";
    ResultSet rs = stmt.executeQuery(sql);

//    def sql = Sql.newInstance("jdbc:mysql://m7-qa-test03:3306/sage_sdk", "root", "root")
//    query = "INSERT INTO func_test (name, version, total, passed, unknown, skipped, failed, broken, create_time) VALUES ('${JOB_NAME}', '${version}', " +
//            "${total}, ${passed}, ${unknown}, ${skipped}, ${failed}, ${broken}, NOW())"
//    sql.close()
}