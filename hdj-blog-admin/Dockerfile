FROM node:alpine3.12 as build-stage
# 设置工作目录
WORKDIR /app
# 复制package.json以及package-lock.json
COPY package*.json ./
# 安装依赖
RUN npm install
# 复制项目文件到当前工作目录
COPY . .
# 构建项目
RUN npm run build

# 生产阶段(用nginx简洁版镜像)
from nginx:1.19.6-alpine as production-stage

MAINTAINER jiajian.java@gmail.com

# 直接复制构建阶段生成在dist的代码到nginx的html目录下
COPY --from=build-stage /app/dist /usr/share/nginx/html
COPY nginx.conf /etc/nginx/nginx.conf
# 对外暴露nginx端口
EXPOSE 80
