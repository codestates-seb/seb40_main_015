#!/bin/bash

BUILD_JAR=$(ls /home/ubuntu/action/backend/build/libs/backend-0.0.1-SNAPSHOT.jar)
JAR_NAME=$(basename $BUILD_JAR)

echo "> 현재 시간: $(date)" >> /home/ubuntu/action/backend/deploy.log

echo "> build 파일명: $JAR_NAME" >> /home/ubuntu/action/backend/deploy.log

echo "> build 파일 복사" >> /home/ubuntu/action/backend/deploy.log
DEPLOY_PATH=/home/ubuntu/action/backend/build/libs
cp $BUILD_JAR $DEPLOY_PATH

echo "> 현재 실행중인 애플리케이션 pid 확인" >> /home/ubuntu/action/backend/deploy.log
CURRENT_PID=$(pgrep -f $JAR_NAME)

if [ -z $CURRENT_PID ]
then
  echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다." >> /home/ubuntu/action/backend/deploy.log
else
  echo "> kill -9 $CURRENT_PID" >> /home/ubuntu/action/backend/deploy.log
  sudo kill -9 $CURRENT_PID
  sleep 5
fi


DEPLOY_JAR=$DEPLOY_PATH$JAR_NAME
echo "> DEPLOY_JAR 배포"    >> /home/ubuntu/action/backend/deploy.log
#sudo nohup java -jar $DEPLOY_JAR --spring.profiles.active=rds 1> /home/ubuntu/action/backend/deploy.log 2>/home/ubuntu/action/backend/deploy_err.log
sudo nohup java -jar $DEPLOY_JAR 1> /home/ubuntu/action/backend/deploy.log 2>/home/ubuntu/action/backend/deploy_err.log