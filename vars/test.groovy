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

//@Field int passed
//@Field int failed
//@Field int skipped
//@Field int broken
//@Field int unknown
//@Field int total
@Field Map<String, Map<String, Integer>> map = new HashMap<>()

@NonCPS
def getResultFromAllure() {
    def reportURL = ""
    if (env.BRANCH_NAME != "" && env.BRANCH_NAME != null) {
        reportURL = "/view/API/job/${jobName}/job/${env.BRANCH_NAME}/${BUILD_NUMBER}/allure/"
    } else {
        reportURL = "/view/API/job/${JOB_NAME}/${BUILD_NUMBER}/allure/"
    }

//    reportURL = "/view/API/job/sage-sdk-test/185/allure/"

    HTTPBuilder http = new HTTPBuilder(jenkinsURL)
    //根据responsedata中的Content-Type header，调用json解析器处理responsedata
//    http.get(path: "${reportURL}widgets/summary.json") { resp, json ->
//        println resp.status
//        passed = Integer.parseInt((String) json.statistic.passed)
//        failed = Integer.parseInt((String) json.statistic.failed)
//        skipped = Integer.parseInt((String) json.statistic.skipped)
//        broken = Integer.parseInt((String) json.statistic.broken)
//        unknown = Integer.parseInt((String) json.statistic.unknown)
//        total = Integer.parseInt((String) json.statistic.total)
//    }

    http.get(path: "${reportURL}data/behaviors.json") { resp, json ->
        List featureJson = json.children

        for (int i = 0; i < featureJson.size(); i++) {
            String featureName = featureJson.get(i).name
            Map<String, Integer> results = new HashMap<>()
            results['passed'] = 0
            results['failed'] = 0
            results['skipped'] = 0
            results['broken'] = 0
            results['unknown'] = 0


            List storyJson = featureJson.get(i).children
            for (int j = 0; j < storyJson.size(); j++) {

                List caseJson = storyJson.get(j).children
                for (int k = 0; k < caseJson.size(); k++) {
                    def caseInfo = caseJson.get(i)
                    String status = caseInfo.status
                    int num = results.get(status) + 1
                    results[status] = num

                }
            }
            int total = 0
            results.each { key, value ->
                total = total + value
            }
            results['total'] = total
            map.put(featureName, results)
        }


    }
}

def call() {
    def version = "release/3.8.2"
    getResultFromAllure()

    println(sqlString)
    getDatabaseConnection(type: 'GLOBAL') {
        map.each { feature, valueMap ->
            def sqlString = "INSERT INTO func_test (name, build_id, feature, version, total, passed, unknown, skipped, failed, broken, create_time) VALUES ('${JOB_NAME}', '${BUILD_ID}', '${feature}', '${version}', " +
                    "${valueMap['total']}, ${valueMap['passed']}, ${valueMap['unknown']}, ${valueMap['skipped']}, ${valueMap['failed']}, ${valueMap['broken']}, NOW())"

            sql sql: sqlString
        }


//        valueMap.each { status, value ->
//            getDatabaseConnection(type: 'GLOBAL') {
//                def sqlString = "INSERT INTO func_test (name, build_id, feature, version, total, passed, unknown, skipped, failed, broken, create_time) VALUES ('${JOB_NAME}', '${BUILD_ID}', '${feature}', '${version}', " +
//                        "${value['total']}, ${value['passed']}, ${value['unknown']}, ${value['skipped']}, ${value['failed']}, ${value['broken']}, NOW())"
//                sql sql: sqlString
//            }
//        }
    }
}


