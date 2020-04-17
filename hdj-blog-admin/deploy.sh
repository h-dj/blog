#!/bin/bash
### 部署脚本

# 发生错误，终止
set -e

rm -rf ./dist/**

#打包
npm run build:$1

cd ./dist

#压缩，dist 文件夹
tar -zcvf hdj-blog-admin.tar.gz ./**

ssh bbs "mkdir -p /home/hdj/nginx/www/admin/"

#发送的远程
scp  ./hdj-blog-admin.tar.gz  bbs:/home/hdj/nginx/www/admin/


#远程解压,启动 bbs 配置的别名 原来是 username@ip
ssh bbs "cd /home/hdj/nginx/www/admin && tar -zxvf ./hdj-blog-admin.tar.gz" 