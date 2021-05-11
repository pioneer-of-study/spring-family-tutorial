# spring-family-tutorial
简介：一份由浅入深的spring全家桶教程,包含Spring Core、Spring Context、Spring AOP、Spring DAO、Spring ORM、Spring Web MVC、Spring Security 共7个模块的简单入门介绍及原理介绍，并对模块都创建了demo项目，原理介绍部分的demo项目为模块名+principle。

人员：胡强、李世宗、韩峰、崔宇航、宋兴文、许妍、朱玉麟.

-----------------

## 简单入门介绍

### Spring Core

### Spring Context

### Spring AOP

### Spring DAO

### Spring ORM

#### 1.ORM是什么，为什么要使用ORM

对象-关系映射（Object-Relational Mapping，简称ORM），面向对象的开发方法是当今企业级应用开发环境中的主流开发方法，关系数据库是企业级应用环境中永久存放数据的主流数据存储系统。对象和关系数据是业务实体的两种表现形式，业务实体在内存中表现为对象，在数据库中表现为关系数据。内存中的对象之间存在关联和继承关系，而在数据库中，关系数据无法直接表达多对多关联和继承关系。因此，对象-关系映射(ORM)系统一般以中间件的形式存在，主要实现程序对象到关系数据库数据的映射。

当我们实现一个应用程序时（不使用O/R Mapping），我们可能会写特别多数据访问层的代码，从数据库保存、删除、读取对象信息，而这些代码都是重复的。而使用ORM则会大大减少重复性代码。对象关系映射（Object Relational Mapping，简称ORM），主要实现程序对象到关系数据库数据的映射。

#### 2.实现一个简单的ORM持久层框架

结构设计：![orm分层设计](README.assets/orm分层设计.png)

**第一层为配置层：**

miniORM.cfg.xml 是框架的核心配置文件，主要用来设置数据库连接信息和映射配置文件路径信息
Xxx.mapper.xml 是框架的映射配置文件，主要用来设置类和表之间以及属性和字段之间的映射关系
Xxx.java 是带有映射注解的实体类，主要用来设置类和表之间以及属性和字段之间的映射关系，和 Xxx.mapper.xml 的作用一样，只不过采用的是注解方式，两者二选一

**第二层为解析层**

Dom4jUtil 类用来解析 miniORM.cfg.xml 和Xxx.mapper.xml 两个配置文件的数据
AnnotationUtil 类用来解析实体类中的映射注解

**第三层为封装层**

ORMConfig 类用来封装和存储从 miniORM.cfg.xml 文件中解析得到的数据
Mapper 类用来封装和存储从 Xxx.mapper.xml 或实体类中解析得到的映射数据

**第四层为功能层**

ORMSession 类主要用来从 ORMConfig 和 Mapper 中获取相关数据，然后生成 sql 语句， 最后通过对 JDBC 的封装最终实现增删改查功能

**核心代码在原理板块展示说明**

### Spring Web MVC 

### Spring Security

1. 初始化项目
   - 导航到[https://start.spring.io](https://start.spring.io/)。该服务提取应用程序所需的所有依赖关系，并为您完成大部分设置。
   - 选择Maven以及Java。
   - 单击**Dependencies，**然后选择**Spring Web**和**Thymeleaf**。
   - 点击**生成**。
   - 下载生成的ZIP文件，该文件是使用您的选择配置的Web应用程序的压缩文件。

2. 创建一个不安全的Web应用程序

   该Web应用程序包括两个简单的视图：主页和“ Hello，World”页面。主页在以下Thymeleaf模板（来自中`src/main/resources/templates/home.html`）中定义：

   ```
   <!DOCTYPE html>
   <html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="https://www.thymeleaf.org" xmlns:sec="https://www.thymeleaf.org/thymeleaf-extras-springsecurity3">
       <head>
           <title>Spring Security Example</title>
       </head>
       <body>
           <h1>Welcome!</h1>
           
           <p>Click <a th:href="@{/hello}">here</a> to see a greeting.</p>
       </body>
   </html>
   ```

   此简单视图包括指向`/hello`页面的链接，该链接在以下Thymeleaf模板（来自中`src/main/resources/templates/hello.html`）中定义：

   ```
   <!DOCTYPE html>
   <html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="https://www.thymeleaf.org"
         xmlns:sec="https://www.thymeleaf.org/thymeleaf-extras-springsecurity3">
       <head>
           <title>Hello World!</title>
       </head>
       <body>
           <h1>Hello world!</h1>
       </body>
   </html>
   ```

   该Web应用程序基于Spring MVC。因此，您需要配置Spring MVC并设置视图控制器以公开这些模板。以下清单（来自`src/main/java/com/example/securingweb/MvcConfig.java`）显示了一个在应用程序中配置Spring MVC的类：

   ```
   package com.example.securingweb;
   
   import org.springframework.context.annotation.Configuration;
   import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
   import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
   
   @Configuration
   public class MvcConfig implements WebMvcConfigurer {
   
   	public void addViewControllers(ViewControllerRegistry registry) {
   		registry.addViewController("/home").setViewName("home");
   		registry.addViewController("/").setViewName("home");
   		registry.addViewController("/hello").setViewName("hello");
   		registry.addViewController("/login").setViewName("login");
   	}
   
   }
   ```

   该`addViewControllers()`方法（将覆盖`WebMvcConfigurer`中的同名方法）添加了四个视图控制器。其中两个视图控制器引用名称为`home`（即`home.html`）的视图，另一个视图控制器引用名为`hello`（即`hello.html`）的视图。第四个视图控制器引用另一个名为`login`的视图。您将在下一部分中创建该视图。

   此时，您可以点击“ Run the Application ”并运行应用程序，访问 [http://localhost:8080/home]() 和 http://localhost:8080/hello，而无需登录任何内容。

3. 使用Spring Security

   假设您要防止未经授权的用户查问候语页面`/hello`。现在，如果访问者单击主页上的链接，他们将看到问候，没有任何障碍可以阻止他们。您需要添加一个屏障，以迫使访问者登录才能看到该页面。

   您可以通过在应用程序中配置Spring Security来实现。如果Spring Security在类路径中，则Spring Boot会使用“基本”身份验证自动保护所有HTTP端点。但是，您可以进一步自定义安全设置。您需要做的第一件事是将Spring Security添加到类路径中。

   使用Maven，您需要向`pom.xml`中的`<dependencies>`元素添加两个额外的条目（一个用于应用程序，一个用于测试），如以下清单所示：

   ```
   <dependency>
     <groupId>org.springframework.boot</groupId>
     <artifactId>spring-boot-starter-security</artifactId>
   </dependency>
   <dependency>
     <groupId>org.springframework.security</groupId>
     <artifactId>spring-security-test</artifactId>
     <scope>test</scope>
   </dependency>
   ```

   以下安全配置（来自`src/main/java/com/example/securingweb/WebSecurityConfig.java`）确保只有经过身份验证的用户才能看到问候：

   ```
   package com.example.securingweb;
   
   import org.springframework.context.annotation.Bean;
   import org.springframework.context.annotation.Configuration;
   import org.springframework.security.config.annotation.web.builders.HttpSecurity;
   import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
   import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
   import org.springframework.security.core.userdetails.User;
   import org.springframework.security.core.userdetails.UserDetails;
   import org.springframework.security.core.userdetails.UserDetailsService;
   import org.springframework.security.provisioning.InMemoryUserDetailsManager;
   
   @Configuration
   @EnableWebSecurity
   public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
   	@Override
   	protected void configure(HttpSecurity http) throws Exception {
   		http
   			.authorizeRequests()
   				.antMatchers("/", "/home").permitAll()
   				.anyRequest().authenticated()
   				.and()
   			.formLogin()
   				.loginPage("/login")
   				.permitAll()
   				.and()
   			.logout()
   				.permitAll();
   	}
   
   	@Bean
   	@Override
   	public UserDetailsService userDetailsService() {
   		UserDetails user =
   			 User.withDefaultPasswordEncoder()
   				.username("user")
   				.password("password")
   				.roles("USER")
   				.build();
   
   		return new InMemoryUserDetailsManager(user);
   	}
   }
   ```

   本类`WebSecurityConfig`使用了`@EnableWebSecurity`注释，开启了Spring Security的网络安全支持，并提供了Spring MVC的整合。它还继承了`WebSecurityConfigurerAdapter`并覆盖了其一些方法来设置Web安全配置的某些细节。

   该`configure(HttpSecurity)`方法定义应保护哪些URL路径，不应该保护哪些URL路径。具体来说，`/`和`/home`路径配置为不需要任何身份验证。所有其他路径都必须经过验证。

   用户成功登录后，他们将被重定向到先前需要身份验证的页面。有一个自定义`/login`页面（由`loginPage()`指定），并且每个人都可以查看它。

   该`userDetailsService()`方法在内存中存储了一个用户信息。该用户的用户名为`user`，密码为`password`，角色为`USER`。

   现在，您需要创建登录页面。该视图已经有一个view controller(视图控制器)`login`，因此您只需创建登录视图本身，如下面的清单（来自`src/main/resources/templates/login.html`）所示：

   ```
   <!DOCTYPE html>
   <html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="https://www.thymeleaf.org"
         xmlns:sec="https://www.thymeleaf.org/thymeleaf-extras-springsecurity3">
       <head>
           <title>Spring Security Example </title>
       </head>
       <body>
           <div th:if="${param.error}">
               Invalid username and password.
           </div>
           <div th:if="${param.logout}">
               You have been logged out.
           </div>
           <form th:action="@{/login}" method="post">
               <div><label> User Name : <input type="text" name="username"/> </label></div>
               <div><label> Password: <input type="password" name="password"/> </label></div>
               <div><input type="submit" value="Sign In"/></div>
           </form>
       </body>
   </html>
   ```

   此Thymeleaf模板捕获用户名和密码并使用post请求`/login`。根据配置，Spring Security提供了一个过滤器，该过滤器拦截该请求并验证用户身份。如果用户认证失败，则页面将重定向到`/login?error`，并且页面将显示相应的错误消息。成功注销后，您的应用程序将重定向到`/login?logout`，并且页面将显示相应的成功消息。

   最后，您需要为访问者提供一种显示当前用户名并注销的方法。为此，更新，`hello.html`向当前用户打个招呼，并包含一个`Sign Out`按钮，如以下清单（来自`src/main/resources/templates/hello.html`）所示：

   ```
   <!DOCTYPE html>
   <html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="https://www.thymeleaf.org"
         xmlns:sec="https://www.thymeleaf.org/thymeleaf-extras-springsecurity3">
       <head>
           <title>Hello World!</title>
       </head>
       <body>
           <h1 th:inline="text">Hello [[${#httpServletRequest.remoteUser}]]!</h1>
           <form th:action="@{/logout}" method="post">
               <input type="submit" value="Sign Out"/>
           </form>
       </body>
   </html>
   ```

4. 从数据库中验证用户名密码

   上面登陆时的用户名密码为程序中写死的，如想从数据库中验证用户名密码，需进行如下配置:

   * 自定义类实现UserDetailService并放入容器中,loadUserByUsername方法中返回通过用户名查找出的User对象（为了方便，这里直接返回的一个User对象）

   * WebSecurityConfig类中注入自己实现的UserDetailService，auth中设置UserDetailService为刚刚注入的UserDetailService
   
   ```
   @Component
   public class MyUserDetailService implements UserDetailsService {
     @Override
     public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       List<GrantedAuthority> auth = AuthorityUtils.commaSeparatedStringToAuthorityList("role");
       return new User("lucy",new BCryptPasswordEncoder().encode("123"),auth);
     }
   }
   ```
   
   
   
   ```
     @Bean
     public PasswordEncoder passwordEncoder(){
       return new BCryptPasswordEncoder();
     }
   
     @Autowired
     UserDetailsService userDetailsService;
   
     // 通过数据库查询用户名密码
     @Override
     protected void configure(AuthenticationManagerBuilder auth) throws Exception{
       auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
     }
   ```
   
5. 基于权限或角色进行访问控制

   上面讲述了用户登录时的认证方式：通过代码中写死的用户名密码登录、通过数据库用户名密码认证。下面讲述用户访问资源时的授权。

   在WebSecurityConfig中修改configure(HttpSecurity http)方法为如下：

   ```
   @Override
     protected void configure(HttpSecurity http) throws Exception{
       http
           .authorizeRequests()
               .antMatchers("/","/home").permitAll()//设置哪些路径可以直接访问
               .antMatchers("/hello").hasAuthority("admin")//设置当前路径，需要登陆用户有哪些权限才可访问
               .anyRequest().authenticated()//所有请求都需要权限认证（登陆认证）
               .and()
           .formLogin()//自定义自己编写的登陆页面
               .loginPage("/login")//登陆页面设置
               .permitAll()
               .and()
           .logout().permitAll();
     }
   ```

   使用lucy、123进行登录后访问/hello页面报403，因为lucy无"admin"权限，无法访问/hello路径，此时需修改MyUserDetailService，给返回的lucy User对象加上amdin权限即可:

   ```
   @Component
   public class MyUserDetailService implements UserDetailsService {
     @Override
     public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       List<GrantedAuthority> auth = AuthorityUtils.commaSeparatedStringToAuthorityList("admin");
       return new User("lucy",new BCryptPasswordEncoder().encode("123"),auth);
     }
   }
   ```

   

   * hasAuthority : 当前主体具有指定的权限时,才能访问

   * hasAnyAuthority: 当前主体具有某一个权限时，均可以访问

   * hasRole:当前主体具有指定的角色时,才能访问

   * hasAnyRole:当前主体具有某一个角色时，均可以访问

     注：role其实是特殊的authority，spring security会自动为role封装一个ROLE\_前缀,因此在数据库存储用户的角色时需为角色加上ROLE\_前缀

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