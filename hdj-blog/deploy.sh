#!/bin/bash
set -e

jar_file='hdj-blog-0.0.1-SNAPSHOT.jar'

#打包
mvn clean package -Dskip.test=true -P$1

cp ./docker/Dockerfile ./target

cd ./target

#压缩文件
tar -zcvf  hdj-blog-serve.tar.gz  ./$jar_file  ./Dockerfile

ssh bbs "mkdir -p /home/hdj/app"
#上传到服务器
scp ./hdj-blog-serve.tar.gz bbs:/home/hdj/app
#构造镜像
ssh bbs "cd /home/hdj/app"\
        "&& tar -zxvf ./hdj-blog-serve.tar.gz " \
        "&& docker build -t hdj-blog ." \
        "&& docker container stop hdj-blog-serve" \
        "&& docker container rm hdj-blog-serve" \
        "&& docker run -d --name hdj-blog-serve" \
          "-p 127.0.0.1:8181:8181" \
          "--restart always" \
          "-v $PWD/logs:/temp/logs hdj-blog"