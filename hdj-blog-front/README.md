## 博客前台项目

> 基于https://gitee.com/fengziy/Fblog模板 开发

## 安装
```
npm install
```
## 运行
```
npm run serve
```

## 打包
```
npm run build:prod
```

## 部署

1. 设置后台api 
```
#在文件.env.production 修改以下变量
VUE_APP_BASE_API = /api
VUE_APP_PORT = 8282

```
2. 修改 publicPath  作为访问的路径 
```
#vue.config.js
publicPath: isDev ? '/' : '/front/',
```

3. 注意，路由的basepath 与publicPath 一致
```
const isDev = process.env.NODE_ENV === 'development'
const router = new VueRouter({
	base: isDev ? '/' : '/front/',
    ...
})
```
4. 最后把打包好/dist/里的文件放到服务器中
- 上传
```shell script
scp ./dist/**  username@host:/opt/blog/nginx/www/front
或者 , 如果修改了ssh端口 -e "ssh -p 端口" 
rsync -avu --progress  ./dist/**  username@host:/opt/blog/nginx/www/front

```
- 启动nginx 
```
#nginx.conf
server {
        #监听端口
        listen  80;
        #编码格式
        charset utf-8;
        #根目录
        root  /home/www/website;

        # 前端门户静态文件资源
        location /front {
            try_files $uri $uri/ /front/index.html;
        }

        # 代理api
        location /api {
            proxy_pass  http://ip:8181/api;
        }
}
```
- 访问 http://ip:port/front