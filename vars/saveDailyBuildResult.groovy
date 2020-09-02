import groovy.grape.Grape
import groovy.sql.Sql

@Grab(group = 'org.codehaus.groovy.modules.http-builder', module = 'http-builder', version = '0.7')
@Grab(group = 'org.jsoup', module = 'jsoup', version = '1.10.3')


import org.jsoup.Jsoup
import groovyx.net.http.HTTPBuilder
import static groovyx.net.http.ContentType.*
import static groovyx.net.http.Method.*
import groovy.transform.Field

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
@Field Map<String, Map<String, Integer>> map = new HashMap<>()

@NonCPS
def getResultFromAllure() {

    echo "this is a test in getResultFromAllure"
    
    def reportURL = "/view/SDP/job/kb-test1/80/allure/"

    HTTPBuilder http = new HTTPBuilder(jenkinsURL)
     echo "this is a testa in getResultFromAllure"
     
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

    echo "this is a testb in getResultFromAllure"
    
}


def call() {
    
    String str = "this is a test"
    echo str
    
    getResultFromAllure()
    echo "this is a test bdfore sql"
	db_url="jdbc:mysql://172.27.234.3:53306/pdms_test"
	username="root"
	password="root"
    driverClass="com.mysql.jdbc.Driver"
	sql = Sql.newInstance(db_url, username, password, driverClass)
	sql.eachRow("select * from SALES"){row ->
            echo row.id
			echo row.path
			def con ="un"
			def path = "D://asd"
			def pan = "E:"
			//sql.execute("insert into data_path (s_size, b_size, con,path,pan) values (1000, 10,${con}, ${path}, ${pan})")
	}
		
}


