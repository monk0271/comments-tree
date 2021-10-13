# CommentTree
  本项目是面试作业
##  设计思路
  采用springboot+vue+jwt+mybaties开发框架。经过测试，基本完全响应所有需求。
实现了前后端分离；后端提供restful API实现注册、登录、获取评论留言、发表评论留言等功能；
利用session+token实现remember me和鉴权功能；利用java集合操作，只查询一次数据库，实现无限回复树生成，O(2n)复杂度。

##  运行说明
### 数据库
数据库为sqlit，建表脚本 db/DDL.sql
### 修改配置文件 application.yml
```xml
spring:
  datasource:
    driver-class-name: org.sqlite.JDBC
    url: 你的sqlit数据文件
```
### 后端启动服务
使用maven启动，在后端根目录comments下打开命令行，输入
```
mvn spring-boot:run
```
端口号8090
### 前端启动服务
在前端根目录comments-front-end下打开命令行，输入命令
```
npm install
```
安装成功后，再输入命令
```
npm run serve
```
端口号8080
### 待优化功能
