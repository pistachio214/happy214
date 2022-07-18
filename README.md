<p align="center">
  Spring-Boot 开源通用后台管理系统模板
</p>

<span id="nav-1"></span>

<span id="nav-2"></span>

<span id="nav-3"></span>

## 项目介绍

此项目是一个 Spring Boot 开发的单机版后台管理系统模板。

<span id="nav-3-1"></span>

### 背景

在小型项目开发的时候，我们总是要重头开始搭建一个后台的数据管理系统，我就在想是否可以搭建一个通用的后台接口的架构，这个项目就是这么来的

<span id="nav-4"></span>

<span id="nav-6"></span>

## 架构

```
|—— cloud                             Java服务
  |—— happy-common                    公共模块
  |—— happy-framework                 框架模块
  |—— happy-system                    系统模块
  |—— happy-admin                     后台接口模块
  | |—— system                        后台系统接口
  |—— .gitignore                      git忽略文件
  |—— pom.xml                         maven配置文件
  |—— README.md                       README
|—— template                          管理页面
  |—— src                             核心代码
```

## 作者

- [happy211](https://github.com/RogerPeng123) - 项目作者，全栈工程师。

</details>

<span id="nav-12"></span>

## 特别感谢

- Spring boot
- Ant Design

<span id="nav-15"></span>

## 版权许可

[License MIT](LICENSE)
