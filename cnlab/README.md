# 部署说明

### mail-server

##### 数据库

执行sql脚本(mysql)，见sql目录下的db.sql

##### 配置文件修改

主要修改如下几项

``` 
spring.datasource.url = 
spring.datasource.username =
spring.datasource.password =
mail.server.smtp = 
mail.server.host = 
mail.server.port = 
```

### mail-client

##### mail-client应在mail-server启动后启动

##### 配置文件修改

``` 
spring.datasource.url = 
spring.datasource.username = 
spring.datasource.password = 
mail.attachments.cache.path = 
mail.attachments.cache.save.path = 
mail.host = 
```

