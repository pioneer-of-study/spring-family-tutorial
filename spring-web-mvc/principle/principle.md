# Spring-web-mvc-principle

## 原理介绍

### Spring Web Mvc

#### 1、前端控制器 DispatcherServlet 结构分析

下图为 *DispatcherServlet* 类的相关继承以及关键方法实现，其中最顶层的类是*HttpServlet*类：当前端发起请求时，主要是靠*HttpServlet*类的doGet/doPost方法处理请求。doGet/doPost方法的相关调用及实现如下图：

![截屏2021-05-12 16.36.11](./screenshot/截屏2021-05-12 16.36.11.png)

> 由图可看出，处理请求最重要的方法是 *DispatherServlet* 中的 doDispatch(HttpServletRequest, HttpServletResponse) 方法

#### 2、请求处理的大概流程

1）前端进行请求后，DispatcherServlet收到请求

2）调用doDispatch()方法进行处理：

- getHandler()：根据当前请求地址在HandlerMapping中找到这个请求的映射信息，获取到目标处理器类（处理类)
- getHandlerAdapter()：根据当前处理器类获取到能执行这个处理器方法的适配器HandlerAdapter
- 使用刚才获取到的适配器（AnnotationMethodHandlerAdapter-注解方法适配器）执行目标方法
- 目标方法执行后，会返回ModelAndView对象
- 根据ModelAndView对象的信息转发到具体的页面，并可以在请求域中取出ModelAndView中的模型数据

> > getHandler()细节：如何根据当前请求找到对应的处理类？
> >
> > getHandler()会返回目标处理器类的执行链（HandlerExecutionChain)
> >
> > HandlerMapping(处理器映射)：保存了每一个处理器能处理哪些请求的映射
> >
> > HandlerMappint中有handlerMap，是在ioc容器启动创建Controller对象时扫描每个处理器都能处理什么请求，并将其保存在HandlerMapping的handlerMap属性中，下一次请求过来，在HandlerMapping中寻找请求映射信息

具体HandlerMapping：https://blog.csdn.net/qq_38410730/article/details/79507465

> > 如何找到目标处理器类的适配器，即找到执行目标方法的适配器。
> >
> > ？？遍历handlerAdapters
> >
> > 对于使用注解的方法，拿到AnnotationMethodHandlerAdapter：能解析注解方法的适配器，对于该处理器，处理类中只要有标了注解的这些方法就能使用

#### 3、SpringMVC中的九大组件：DIspatcherServlet中有几个引用类型的属性

> SpringMVC在工作的时候，关键位置都是由这些组件完成

- 文件上传解析器 MultipartResolver
- 区域信息解析器 LocaleResolver (和国际化有关)
- 主题解析器 ThemeResolver（强大的主题效果更换)
- Handler映射信息 HandlerMapping
- Handler的适配器 HandlerAdapter
- 异常解析器 HandlerExceptionResolver 
- 视图名解析器 RequestToViewNameTranslator
- FlashMapManager(SpringMVC中运行重定向携带数据的功能)
- 视图解析器   ViewResolver

九大组件全是接口：接口就是规范，提供了非常强大的拓展性

