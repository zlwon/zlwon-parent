### zlwon
## 项目简介：
使用SpringBoot+Motan+Zookeeper搭建的Restful服务，对外提供HTTP接口

## 项目结构：
` service：RPC接口`
` web：管理后台，前端使用thymeleaf模板`
` api：对外接口`

web和api通过调用service层的RPC接口获得数据


架构支撑：
` zookeeper`
` redis`
` mongodb`