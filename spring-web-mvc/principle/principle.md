# Spring-web-mvc-principle

## 原理介绍

### Spring Web Mvc

#### 1、前端控制器的架构 DispatcherServlet

下图为 *DispatcherServlet* 类的相关继承以及关键方法实现，其中最顶层的类是*HttpServlet*类：当前端发起请求时，主要是靠*HttpServlet*类的doGet/doPost方法处理请求。doGet/doPost方法的相关调用及实现如下图：

![截屏2021-05-12 16.36.11](./screenshot/截屏2021-05-12 16.36.11.png)

> 由图可看出，处理请求最重要的方法是 *DispatherServlet* 中的 doDispatch(HttpServletRequest, HttpServletResponse) 方法

