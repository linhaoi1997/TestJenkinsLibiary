import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit


//// How many threads to kick off
//def nThreads = 3
//def pool = Executors.newFixedThreadPool(nThreads)
//
//
//
//
//pool.submit({
//    println("123123123123123")
//})
//
//def timeout = 3
//pool.shutdown()
//println(33)
//pool.awaitTermination(timeout, TimeUnit.SECONDS)
//pool.shutdownNow()
//println(44)

//def call(List<String> marks, int timeout=60){
//    def pool = Executors.newFixedThreadPool( marks.size() )
//
//    for (int i = 0; i < marks.size(); i ++){
//        println("提交任务：${marks.get(i)}")
//        pool.submit({
//            println("开启线程")
//            sh """
//            python3 -m pytest test/ -m "${marks.get(i)}
//            """
//
////            def command = """python3 -m pytest test/ -m "${marks.get(i)}"""
////            def proc = command.execute()
////            proc.waitFor()
////            println "return code: ${ proc.exitValue()}"
////            println "stderr: ${proc.err.text}"
////            println "stdout: ${proc.in.text}"
//        })
//    }
//    pool.shutdown()
//    println(11111111)
//    pool.awaitTermination( timeout, TimeUnit.MINUTES )
//    println(22222222)
//    pool.shutdownNow()
//    println(33333333)
//
//
//}


def call(List<String> marks, int timeout = 60) {
    for (int i = 0; i < marks.size(); i++) {
        sh """
        python3 -m pytest test/ -m "${marks.get(i)}"
        """
    }
//    def mytask = { mark ->
////        sh """
////            python3 -m pytest test/ -m "${mark}
////            """
//        println("fasdfsadfsdfsadfsadf")
//    }
//
//    def threads = new ArrayList<Thread>()
//
//    for (int i = 0; i < marks.size(); i++) {
//        def t = new Thread({
//            mytask(marks.get(i))
//
//        })
//        t.start()
//        threads.add(t)
//    }
//
//    for (int i = 0; i < threads.size(); i++) {
//        threads.get(i).join(timeout * 1000 * 60)
//        println(111111111)
//    }

}


