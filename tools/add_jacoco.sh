#!/bin/bash
DOWNLOAD_JACOCO_FROM="ftp://m7-qa-test03:21213/jacoco/jacocoagent.jar"
#k set env deploy/$DEPLOY_NAME -n $NAMESPACES JACOCO_AGENT_ENABLED=$JACOCO_AGENT_ENABLED JACOCO_AGENT_PORT=$JACOCO_AGENT_PORT JACOCO_AGENT_INCLUDES=$JACOCO_AGENT_INCLUDES JACOCO_AGENT_APPEND=$JACOCO_AGENT_APPEND DOWNLOAD_JACOCO_FROM=$DOWNLOAD_JACOCO_FROM

echo $NAMESPACES
echo "##############"
if [ "$?" == 1 ]
then
   exit 1
fi
if [ ! -d jacoco_svc  ];then
  mkdir jacoco_svc
else
  echo dir exist
fi
pushd jacoco_svc
cat>svc_jacoco.yaml<<EOF
apiVersion: v1
kind: Service
metadata:
  labels:
    app: $APP_NAME
  name: jacoco-agent
spec:
  ports:
  - name: jacoco
    port: 30667
    protocol: TCP
    targetPort: $JACOCO_AGENT_PORT
    nodePort: 30633
  selector:
    app: $APP_NAME
  type: NodePort
status:
  loadBalancer: {}
EOF
#k apply -f svc_jacoco.yaml -n $NAMESPACES
if [ "$?" == 1 ]
then
   exit 1
fi      