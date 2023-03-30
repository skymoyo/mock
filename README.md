# 简介

>  提供的低入侵mock工具
 - 实现 com.alibaba.dubbo:2.6.0 mock
 - classpath:MockAgent 指定需要代理的方法

# 版本升级

## 0.0.3
 
**feature:**
- ProxyScanAgent  通过 mock.proxy.file 配置取文件，未配置取 classpath:MockAgent
- 优化了部分代码

**fix:**
 - [RpcManager 导致 OOM](https://gitee.com/skymoyo/mock/issues/I5YAUG)   
 - 通讯client 发送时 应该调用解密方法，而非加密

## 0.0.2
 
**feature:**
- 代理mock-agent 改成SPI 加载方式
- 通过 classpath:MockAgent 指定需要代理的方法
- 经过代理后通过 mock.config.enable 配置 控制是否进行mock

**fix:**
 - netty客户端超过1024字节接口不完整
 - agent代理方法retureClass是pojo导致编译失败

## 0.0.1

- dubbo 2.6.0 代理


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
#mock.config.prefix=/mock/allReq
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


