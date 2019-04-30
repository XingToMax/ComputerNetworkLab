# 邮件客户端

> 核心是基于Socket实现的SMTP客户端，支持发送邮件到SMTP服务端，支持部分MIME协议内容，客户端用户界面采用B/S架构

### 基本组成

+ 邮件发送模块

  基于Socket实现了SMTP邮件发送命令，需要与SMTP服务器建立连接，并逐步发送邮件数据，支持附件

+ 邮件解析模块

  获取邮件数据后，解析其首部和主体部分，支持multipart/mixed类型主体的解析

+ 注册登录模块

  提供注册登录界面及相应的业务逻辑

+ 邮件阅读模块

  提供收发邮件、查看已发送、查看收件箱的界面及相应的业务逻辑

### 主要技术和框架

+ Java Socket
+ Spring Boot
+ Spring Data JPA + Hibernate
+ MySQL
+ JQuery
+ Layui