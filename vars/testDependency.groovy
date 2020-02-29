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


def call(String[] marks, int timeout=60){
    def pool = Executors.newFixedThreadPool( marks.size() )

    for (int i = 0; i <= marks.size(); i ++){
        pool.submit({
            sh "python3 -m pytest -m ${mark}"
        })
    }
    pool.shutdown()
    pool.awaitTermination( timeout, TimeUnit.MINUTES )
    pool.shutdownNow()

}