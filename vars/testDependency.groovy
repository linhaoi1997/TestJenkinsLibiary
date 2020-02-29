import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit


//// How many threads to kick off
def nThreads = 3
def pool = Executors.newFixedThreadPool( nThreads )




pool.submit({
    println("123123123123123")
})

def timeout = 3
pool.shutdown()
println(33)
pool.awaitTermination( timeout, TimeUnit.SECONDS )
pool.shutdownNow()
println(44)


def call(List<String> marks, int timeout=60){
    def pool = Executors.newFixedThreadPool( marks.size() )

    for (int i = 0; i < marks.size(); i ++){
        println("提交任务：${marks.get(i)}")
        pool.submit({
            sh """
            python3 -m pytest test/ -m "${marks.get(i)}
            """

//            def command = """python3 -m pytest test/ -m "${marks.get(i)}"""
//            def proc = command.execute()
//            proc.waitFor()
//            println "return code: ${ proc.exitValue()}"
//            println "stderr: ${proc.err.text}"
//            println "stdout: ${proc.in.text}"
        })
    }
    println(11111111)
    pool.awaitTermination( timeout, TimeUnit.MINUTES )
    println(22222222)
    pool.shutdownNow()
    println(33333333)


}