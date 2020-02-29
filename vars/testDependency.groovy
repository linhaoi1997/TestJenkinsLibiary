import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit


//// How many threads to kick off
//def nThreads = 3
//def pool = Executors.newFixedThreadPool( nThreads )
//
//
//
//
//pool.submit({
//    sh "python3 -m pytest -m ${mark}"
//})
//
//def timeout = 3
//pool.shutdown()
//println(33)
//pool.awaitTermination( timeout, TimeUnit.SECONDS )
//pool.shutdownNow()
//println(44)


def call(List<String> marks){
    def pool = Executors.newFixedThreadPool( marks.size() )

    for (int i = 0; i <= marks.size(); i ++){
        println("提交任务：${marks.get(i)}")
        pool.submit({
            sh "python3 -m pytest -m ${marks.get(i)}"
        })
    }
    pool.shutdown()
//    pool.awaitTermination( timeout, TimeUnit.MINUTES )
//    pool.shutdownNow()

}