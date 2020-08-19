
/**
 * Created by liyuqing on 20/8/16.
 */


def call(targetHost,hostUser="root" hostPassword="ai4every1_cloudops" isHttps=false, sageVersion="4.0.0", hadoopUser="work", hadoopType="cdh"){
    def run_status = sh (
            script: """echo ${hostPassword} | ssh -t ${hostUser}@${targetHost} "wget http://pkg-plus.4paradigm.com/qa-tools/qa-scripts/init_sage_environment.sh && \
                       sh -x init_sage_environment.sh ${isHttps} ${targetHost} ${sageVersion} ${hadoopUser} ${hadoopType}"
                    """,
            returnStatus:true
    )
    print run_status
    // 失败退出
    if (run_status != 0){
        print '环境初始化执行失败，强制退出'
        sh 'exit 1'
    }
}
