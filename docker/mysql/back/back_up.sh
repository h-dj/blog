#!/bin/bash
set -e
#删除日志函数
cleanLog () {
  #如果文件不存在，则将错误重定向到标准输出
  if ls *.sql >/dev/null 2>&1; then
    #3天前的日期
    three_day_ago=$(date +%Y%m%d --date="-3 day")
    echo "3天前的日期 $three_day_ago"
    #遍历这个数组
    for file in `ls *.sql`
    do
      echo $file
      #获取该文件的创建时间
      datatime=$(echo  $file | sed 's/[^0-9]*//g')
      echo "文件日期：　$datatime"
      #如果这个文件的时间小于三天前的时间则删除这个文件
              if [ $datatime -lt $three_day_ago ]; then
                      rm -rf ./$file
                      git rm ./$file
              fi
    done
  fi
}

cd $1

cleanLog

## 备份数据
## 备份数据库表结构
docker exec blog-mysql sh -c 'exec mysqldump -uroot -p"$MYSQL_ROOT_PASSWORD" -d blog' > ./blog-schema-`date +%Y%m%d`.sql
#备份数据库数据
docker exec blog-mysql sh -c 'exec mysqldump -uroot -p"$MYSQL_ROOT_PASSWORD" -t blog' > ./blog-data-`date +%Y%m%d`.sql
#echo  "备份结束 ===> "


#push 到Github
git pull
git add .
git commit -m "backup blog"
git push