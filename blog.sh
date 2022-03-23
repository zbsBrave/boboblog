#!/bin/bash
pwd
source /etc/profile

#1，杀进程。通过端口号获取PID进程号
PID=`lsof -i:8081 | grep "LISTEN" | awk '{print $2}'`

echo '即将关闭进程号：'$PID
kill -9 $PID


#2，运行
START_COMMAND="/usr/local/java/jdk1.8.0_251/bin/java -jar blog-server.jar --spring.profiles.active=pro"


# 1:stdout标准输出,2:stderr标准错误； 2>&1 >out.log 代表：错误重定向到标准输出，标准输出重定向到文件
# 日志输出到nacos.out，进程号输出到pid文件。  1>/dev/null 2>err.log，正常日志抛弃，异常日志输出到err.log
#nohup $START_COMMAND  1>/dev/null 2>err.log & echo $! > pid
nohup $START_COMMAND  >out.log 2>&1 & echo $! > pid


#通过文件获取pid： PID=$(cat macos.pid  2>/dev/null)

echo '启动成功：'$(cat pid  2>/dev/null)