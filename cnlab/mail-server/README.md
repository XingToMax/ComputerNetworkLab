# 邮件服务器

> 基于socket实现SMTP服务器，负责通过接收客户代理、其他SMTP服务器发送过来的邮件以及转发客户邮件

### 基本组成

+ MailServer + SmtpServerContext

  负责服务端应用的运转并提供运行上下文

+ SocketHandler

  负责处理socket请求，实现SMTP响应部分

+ DataHandler

  负责处理邮件接收，负责实现SMTP转发以及邮件数据持久化

### 使用技术及框架

+ Java Socket + Java ServerSocket
+ Spring Boot
+ Spring Data JPA + Hibernate
+ MySQL