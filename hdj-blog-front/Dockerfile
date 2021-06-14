# 生产阶段(用nginx简洁版镜像)
FROM nginx:1.19.6-alpine as production-stage

WORKDIR /app

MAINTAINER jiajian.java@gmail.com

# 直接复制构建阶段生成在dist的代码到nginx的html目录下
COPY ./dist /app
COPY nginx.conf /etc/nginx/nginx.conf
# 对外暴露nginx端口
EXPOSE 80
