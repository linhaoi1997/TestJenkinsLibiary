/**
 * Created by sungaofei on 20/2/4.
 */


def String call(String templateName){
    def yaml = ""
    switch (templateName){
        case "java":
            def label = "java" + UUID.randomUUID()
            yaml = """
apiVersion: v1
kind: Pod
metadata:
  labels:
    qa: ${label}
spec:
  containers:
  - name: maven
    image: registry.4paradigm.com/tester_jenkins_slave
    imagePullPolicy: Always
    env:
      - name: TZ
        value: "Asia/Shanghai"
    volumeMounts:
      - mountPath: "/mnt"
        name: "chromedownload"
      - mountPath: "/etc/localtime"
        name: "host-time"
    command:
    - cat
    tty: true
  volumes:
    - name: "host-time"
      hostPath:
        path: "/etc/localtime"
    - name: "chromedownload"
      nfs:
        server: 172.27.128.122
        path: "/home/nfs-share"
  imagePullSecrets:
    - name: docker4paradigm

"""

    }
    return yaml
}