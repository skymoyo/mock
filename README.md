# 简介

> 针对com.alibaba.dubbo:2.6.0  提供的低入侵mock工具

# 快速开始

## 项目中引入 mock-client 
> 一个不叫starter 的 starter

提供netty，http 请求方式。

根据实际情况修改配置

```properties
# netty客户端 链接配置
mock.config.host=127.0.0.1
mock.config.port=18081
##默认mockNettyClient，可不配置
#mock.config.rpc=mockNettyClient
# http客户端 链接配置
#mock.config.host=http://127.0.0.1
#mock.config.port=8081
#mock.config.prefix=/mock/allReq/
#mock.config.rpc=mockHttpClient
#激活mock
mock.config.enable=true
#用于数据加解密
mock.config.compile=defCompile
```

## 应用服务器启动添加 -javaagent:{路径}/mock-agent-0.0.1.jar

## mock-core

提供netty，http 接口方式。

- mock_config表定义url配置，
- mock_rule表定义不同的规则和对应的返回
- mock_condition表针对规则做的不同的判断条件，具有 PARAMS、HEAD、ROUTE  3种获取方式
  - PARAMS： 请求参数, (解析参数使用SpringEL)
  - HEAD ：http请求头,(解析参数使用SpringEL)
  - ROUTE : 包限定名转的url或url 

## 参考

mock.test.dubbo.cusmer.TestServiceTest

# 模块
 - mock-agent: javassist 修改字节码
 - mock-client: 与服务器通讯的客户端
 - mock-common: 通用包
 - mock-core: mock 服务端
 - mock-rpc: netty 客户端
 - mock-test: 一些测试内容

# todo
mock-core 响应结果可参数配置化!!!
