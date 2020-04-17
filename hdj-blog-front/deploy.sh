#!/bin/bash
### 部署脚本

# 发生错误，终止
set -e

rm -rf ./dist/**

#打包
npm run build:$1

cd ./dist

#压缩，dist 文件夹
tar -zcvf hdj-blog-front.tar.gz ./**


ssh bbs "mkdir -p /home/hdj/nginx/www/front/" 

#发送的远程
scp  ./hdj-blog-front.tar.gz  bbs:/home/hdj/nginx/www/front/


#远程解压,启动
ssh bbs "cd /home/hdj/nginx/www/front/ && tar -zxvf ./hdj-blog-front.tar.gz" 

