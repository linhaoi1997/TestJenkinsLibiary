#!/bin/bash
NAMESPACES=$1
DEPLOY_NAME=$2
JACOCO_AGENT_ENABLED=$3
JACOCO_AGENT_INCLUDES=$4
JACOCO_AGENT_PORT=$5
JACOCO_AGENT_APPEND=$6
JACOCO_APP_NAME=$7

DOWNLOAD_JACOCO_FROM="ftp://m7-qa-test03:21213/jacoco/jacocoagent.jar"
echo "#####"
echo $1
echo $2
echo $NAMESPACES
echo $DEPLOY_NAME
echo $JACOCO_AGENT_ENABLED
echo $JACOCO_AGENT_INCLUDES
echo $JACOCO_AGENT_PORT
echo $JACOCO_AGENT_APPEND
echo $JACOCO_APP_NAME

echo $JACOCO_AGENT_PORT
echo $JACOCO_APP_NAME
echo "########"
k set env deploy/$DEPLOY_NAME -n $NAMESPACES JACOCO_AGENT_ENABLED=$JACOCO_AGENT_ENABLED JACOCO_AGENT_PORT=$JACOCO_AGENT_PORT JACOCO_AGENT_INCLUDES=$JACOCO_AGENT_INCLUDES JACOCO_AGENT_APPEND=$JACOCO_AGENT_APPEND DOWNLOAD_JACOCO_FROM=$DOWNLOAD_JACOCO_FROM
if [ "$?" == 1 ]
then
   exit 1
fi
rm -rf jacoco_svc
mkdir jacoco_svc
pushd jacoco_svc
cat>svc_jacoco.yaml<<EOF
apiVersion: v1
kind: Service
metadata:
  labels:
    app: $JACOCO_APP_NAME
  name: jacoco-agent
spec:
  ports:
  - name: jacoco
    port: 30667
    protocol: TCP
    targetPort: $JACOCO_AGENT_PORT
    nodePort: 30633
  selector:
    app: $JACOCO_APP_NAME
  type: NodePort
status:
  loadBalancer: {}
EOF
k apply -f svc_jacoco.yaml -n $NAMESPACES
if [ "$?" == 1 ]
then
   exit 1
fi      