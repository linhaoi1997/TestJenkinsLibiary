
/**
 * Created by liyuqing on 20/8/16.
 */


def call(targetHost,isHttps=false, sageVersion="4.0.0", hadoopUser="work", hadoopType="cdh"){
    public_key="ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQDAFrsiXGbTcdONpshr8iKqszKdKmhV/m6XkEGY2eCqLcmxsfOrLVgnV91r4bByRUg2nYp1/hSW6641AM6Yp1piN0p66AqymZgj95f/7vB1jXTLWlnvxUjMRkotqrFW297bb66/x9daNRSGW2TySexZ+j2zyegpZAlEMQUkPipimIfiKO4ybJzosJTex+GIKKbxgH3YQ7YYi3q7vBosfJByZOLx74VS8jkyNBgZkxmUZIG6TY1dcoumUoGV+nWzMJknEyDvXXG94U0C4mTiVXtMkWCqksf/bsttcVAh7wWQ5NdOaUy848ag7J41Ck3cHpQWHirf4M2qTYVaQhagGOtr root@qa-ops-platform"
    def copy_public_key = sh (
            script: """echo ${public_key} > ~/.ssh/id_rsa.pub""",
            returnStatus: true
    )
    print copy_public_key
    // 失败退出
    if (copy_public_key != 0){
        print '环境初始化执行失败，强制退出'
        sh 'exit 1'
    }

    def run_status = sh (
            script: """ssh root@${targetHost} "wget http://pkg-plus.4paradigm.com/qa-tools/qa-scripts/init_sage_environment.sh && \
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
