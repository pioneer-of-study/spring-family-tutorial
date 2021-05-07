# spring-family-tutorial
简介：一份由浅入深的spring全家桶教程,包含Spring Core、Spring Context、Spring AOP、Spring DAO、Spring ORM、Spring Web MVC、Spring Security 共7个模块的简单入门介绍及原理介绍，并在各模块末尾附上对应的demo代码。

人员：胡强、李世宗、韩峰、崔宇航、宋兴文、许妍、朱玉麟

-----------------

## 简单入门介绍

### Spring Core

### Spring Context

### Spring AOP

### Spring DAO

### Spring ORM

### Spring Web MVC 

### Spring Security





## 原理介绍

### Spring Core

### Spring Context

### Spring AOP

### Spring DAO

### Spring ORM

### Spring Web MVC 

### Spring Security

##### 本质

过滤器链

基本位于最底层的过滤器：FilterSecurityInterceptor
授权过程中处理抛出的异常的过滤器：ExceptionTranslationFilter
用户名密码验证的过滤器:UsernamePasswordAuthenticationFilter



##### 过滤器如何加载的

使用spring security配置过滤器

* DelegatingFilterProxy 的init方法中获取到filterChainProxy bean对象
* filterChainProxy 的doFilter->doFilterInternal->getfilters方法会拿到所有的过滤器链（List<SecurityFilterChain> filterChains），并Returns the first filter chain matching the supplied URL（List<filter>），doFilterInternal中的virtualFilterChain执行完所有刚刚拿到的过滤器链后，再执行底层防火墙的过滤器链.



##### 在实际开发过程中从数据库获取用户名、密码时会用到的两个接口

###### UserDetailService

1. 继承UsernamePasswordAuthenticationFilter，重写attemptAuthentication方法
2. 重写successfulAuthentication方法（认证成功时调用）,重写unsuccessfulAuthentication方法（认证失败时调用）
3. 继承UserDetailService接口，实现loadUserByUsername方法，从数据库查询对应用户数据



###### PasswordEncoder

​		数据加密接口，用于返回user对象里面密码的加密





##### web权限方案

###### 认证

1. 设置登录的用户名和密码

   * 通过配置文件application.yml

     ```
     spring:
       security:
         user:
           name: huq
           password: 123
     ```

     注：需将之前WebSecurityConfig中注入的UserDetailsService注释掉，否则优先使用注入的UserDetailsService中的用户名密码校验方式

   * 通过配置类 

   * 自定义实现类

###### 授权