# spring-family-tutorial
简介：一份由浅入深的spring全家桶教程,包含Spring Core、Spring Context、Spring AOP、Spring DAO、Spring ORM、Spring Web MVC、Spring Security 共7个模块的简单入门介绍及原理介绍，并对模块都创建了demo项目，原理介绍部分的demo项目为模块名+principle。

人员：胡强、李世宗、韩峰、崔宇航、宋兴文、许妍、朱玉麟.

-----------------

# 简单入门介绍

## Spring Core

## Spring Context

### Spring-context-xml配置方式

#### 1.Spring简介

	 Spring是一个开源框架，它由[Rod Johnson](https://baike.baidu.com/item/Rod Johnson)创建。它是为了解决企业应用开发的复杂性而创建的。 
	
	 目前是JavaEE开发的灵魂框架。他可以简化JavaEE开发，可以非常方便整合其他框架，无侵入的进行功能增强。
	
	 Spring的核心就是 控制反转(IoC)和面向切面(AOP) 。

#### 2.IOC控制反转

##### 2.1 概念

	控制反转，之前对象的控制权在类手上，现在反转后到了Spring手上。


​	

##### 2.2 入门案例

###### ①导入依赖

导入SpringIOC相关依赖

~~~~xml
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>5.1.9.RELEASE</version>
        </dependency>
~~~~

###### ②编写配置文件

在resources目录下创建applicationContext.xml文件，文件名可以任意取。但是建议叫applicationContext。

内容如下：

~~~~xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <!--
        classs:配置类的全类名
        id:配置一个唯一标识
    -->
    <bean class="com.zhuyl10.dao.impl.StudentDaoImpl" id="studentDao"  >
    </bean>


</beans>
~~~~



###### ③创建容器从容器中获取对象并测试

~~~~java
    public static void main(String[] args) {

//        1.获取StudentDaoImpl对象
        //创建Spring容器，指定要读取的配置文件路径
        ApplicationContext app = new ClassPathXmlApplicationContext("applicationContext.xml");
        //从容器中获取对象
        StudentDao studentDao = (StudentDao) app.getBean("studentDao");
        //调用对象的方法进行测试
        System.out.println(studentDao.getStudentById(1));
    }
~~~~



##### 2.3 Bean的常用属性配置

###### 2.3.1 id

	bean的唯一标识，同一个Spring容器中不允许重复

###### 2.3.2 class

	全类名，用于反射创建对象

###### 2.3.3 scope 

	scope主要有两个值：singleton和prototype
	
	如果设置为singleton则一个容器中只会有这个一个bean对象。默认容器创建的时候就会创建该对象。
	
	如果设置为prototype则一个容器中会有多个该bean对象。每次调用getBean方法获取时都会创建一个新对象。



#### 3.DI依赖注入

	依赖注入可以理解成IoC的一种应用场景，反转的是对象间依赖关系维护权。


​	

##### 3.1 set方法注入

在要注入属性的bean标签中进行配置。前提是该类有提供属性对应的set方法。

~~~~java
package com.zhuyl10.spdb;public class Student {    private String name;    private int id;    private int age;    private Dog dog;    public Dog getDog() {        return dog;    }    public void setDog(Dog dog) {        this.dog = dog;    }    @Override    public String toString() {        return "Student{" +                "name='" + name + '\'' +                ", id=" + id +                ", age=" + age +                '}';    }    public Student() {    }    public Student(String name, int id, int age) {        this.name = name;        this.id = id;        this.age = age;    }    public String getName() {        return name;    }    public void setName(String name) {        this.name = name;    }    public int getId() {        return id;    }    public void setId(int id) {        this.id = id;    }    public int getAge() {        return age;    }    public void setAge(int age) {        this.age = age;    }}
~~~~

~~~~xml
    <bean class="com.zhuyl10.spdb.Dog" id="dog">        <property name="name" value="小白"></property>        <property name="age" value="6"></property>    </bean>    <bean class="com.zhuyl10.spdb.Student" id="student" >        <!--            name属性用来指定要设置哪个属性            value属性用来设置要设置的值            ref属性用来给引用类型的属性设置值，可以写上Spring容器中bean的id        -->        <property name="name" value="东南枝"></property>        <property name="age" value="20"></property>        <property name="id" value="1"></property>        <property name="dog" ref="dog"></property>    </bean>
~~~~



##### 3.2 有参构造注入

在要注入属性的bean标签中进行配置。前提是该类有提供对应的有参构造。

~~~~java
public class Student {    private String name;    private int id;    private int age;    private Dog dog;    public Student(String name, int id, int age, Dog dog) {        this.name = name;        this.id = id;        this.age = age;        this.dog = dog;    }    //.....省略其他}
~~~~

~~~~xml
    <!--使用有参构造进行注入-->    <bean class="com.zhuyl10.spdb.Student" id="student2" >        <constructor-arg name="name" value="自挂东南枝"></constructor-arg>        <constructor-arg name="age" value="20"></constructor-arg>        <constructor-arg name="id" value="30"></constructor-arg>        <constructor-arg name="dog" ref="dog"></constructor-arg>    </bean>
~~~~



##### 3.3 复杂类型属性注入

实体类如下：

~~~~java
@Data@NoArgsConstructor@AllArgsConstructorpublic class User {    private int age;    private String name;    private Phone phone;    private List<String> list;    private List<Phone> phones;    private Set<String> set;    private Map<String, Phone> map;    private int[] arr;    private Properties properties;}
~~~~

~~~~java
@Data@NoArgsConstructor@AllArgsConstructorpublic class Phone {    private double price;    private String name;    private String password;    private String path;}
~~~~



配置如下：

~~~~xml
<?xml version="1.0" encoding="UTF-8"?><beans xmlns="http://www.springframework.org/schema/beans"       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">    <bean class="com.zhuyl10.spdb.Phone" id="phone">        <property name="price" value="3999"></property>        <property name="name" value="黑米"></property>        <property name="password" value="123"></property>        <property name="path" value="qqqq"></property>    </bean>        <bean class="com.zhuyl10.spdb.User" id="user">        <property name="age" value="10"></property>        <property name="name" value="大队长"></property>        <property name="phone" ref="phone"></property>        <property name="list">            <list>                <value>三更</value>                <value>西施</value>            </list>        </property>        <property name="phones">            <list>                <ref bean="phone"></ref>            </list>        </property>        <property name="set">            <set>                <value>setEle1</value>                <value>setEle2</value>            </set>        </property>        <property name="map">            <map>                <entry key="k1" value-ref="phone"></entry>                <entry key="k2" value-ref="phone"></entry>            </map>        </property>        <property name="arr">            <array>                <value>10</value>                <value>11</value>            </array>        </property>        <property name="properties">            <props>                <prop key="k1">v1</prop>                <prop key="k2">v2</prop>            </props>        </property>    </bean></beans>
~~~~



#### 4.Lombok

##### ①导入依赖

~~~~xml
        <dependency>            <groupId>org.projectlombok</groupId>            <artifactId>lombok</artifactId>            <version>1.18.16</version>        </dependency>
~~~~

##### ②增加注解

~~~~java
@Data //根据属性生成set，get方法@NoArgsConstructor //生成空参构造@AllArgsConstructor //生成全参构造public class Phone {    private double price;    private String name;    private String password;    private String path;}
~~~~



#### 5.SPEL

	我们可以再配置文件中使用SPEL表达式。写法如下:

~~~~xml
        <property name="age" value="#{20}"/>        <property name="car" value="#{car}"/>
~~~~

	注意：SPEL需要写到value属性中，不能写到ref属性。



#### 6.配置文件

##### 6.1 读取properties文件

	我们可以让Spring读取properties文件中的key/value，然后使用其中的值。

###### ①设置读取properties

在Spring配置文件中加入如下标签：指定要读取的文件的路径。

~~~~xml
<context:property-placeholder location="classpath:filename.properties">
~~~~

其中的classpath表示类加载路径下。

我们也会用到如下写法：classpath:**.properties  其中的*  * 表示文件名任意。

**注意：context命名空间的引入是否正确**

###### ②使用配置文件中的值

在我们需要使用的时候可以使用${key}来表示具体的值。注意要再value属性中使用才可以。例如：

~~~~xml
<property name="propertyName" value="${key}"/>
~~~~



##### 6.2 引入Spring配置文件

	我们可以在主的配置文件中通过import标签的resource属性，引入其他的xml配置文件

~~~~xml
<import resource="classpath:applicationContext-book.xml"/>
~~~~



#### 7. 低频知识点

##### 7.1 bean的配置

###### 7.1.1 name属性

	我们可以用name属性来给bean取名。例如：

~~~~xml
    <bean class="com.alibaba.druid.pool.DruidDataSource" id="dataSource" name="dataSource2,dataSource3">        <property name="driverClassName" value="${jdbc.driver}"></property>        <property name="url" value="${jdbc.url}"></property>        <property name="username" value="${jdbc.username}"></property>        <property name="password" value="${jdbc.password}"></property>    </bean>
~~~~

	获取的时候就可以使用这个名字来获取了

~~~~java
    public static void main(String[] args) {        ApplicationContext app = new ClassPathXmlApplicationContext("applicationContext.xml");        DruidDataSource dataSource = (DruidDataSource) app.getBean("dataSource3");        System.out.println(dataSource);    }
~~~~



###### 7.1.2 lazy-init

	可以控制bean的创建时间，如果设置为true就是在第一次获取该对象的时候才去创建。

~~~~xml
    <bean class="com.alibaba.druid.pool.DruidDataSource" lazy-init="true"  id="dataSource" name="dataSource2,dataSource3">        <property name="driverClassName" value="${jdbc.driver}"></property>        <property name="url" value="${jdbc.url}"></property>        <property name="username" value="${jdbc.username}"></property>        <property name="password" value="${jdbc.password}"></property>    </bean>
~~~~



###### 7.1.3 init-method

	可以用来设置初始化方法，设置完后容器创建完对象就会自动帮我们调用对应的方法。

~~~~java
@Data@NoArgsConstructor@AllArgsConstructorpublic class Student {    private String name;    private int id;    private int age;	//初始化方法    public void init(){        System.out.println("对学生对象进行初始化操作");    }}
~~~~

~~~~xml
<bean class="com.zhuyl10.spdb.Student" id="student" init-method="init"></bean>
~~~~

**注意：配置的初始化方法只能是空参的。**



###### 7.1.4 destroy-method

	可以用来设置销毁之前调用的方法，设置完后容器销毁对象前就会自动帮我们调用对应的方法。

~~~~xml
    <bean class="com.zhuyl10.spdb.Student" id="student"  destroy-method="close"></bean>
~~~~

~~~~java
@Data@NoArgsConstructor@AllArgsConstructorpublic class Student {    private String name;    private int id;    private int age;    public void init(){        System.out.println("对学生对象进行初始化操作");    }    public void close(){        System.out.println("对象销毁之前调用，用于释放资源");    }}
~~~~

**注意：配置的方法只能是空参的。**



###### 7.1.5 factory-bean&factory-method

	当我们需要让Spring容器使用工厂类来创建对象放入Spring容器的时候可以使用factory-bean和factory-method属性。



7.1.5.1 配置实例工厂创建对象

配置文件中进行配置

~~~~xml
    <!--创建实例工厂-->    <bean class="com.zhuyl10.factory.CarFactory" id="carFactory"></bean>    <!--使用实例工厂创建Car放入容器-->    <!--factory-bean 用来指定使用哪个工厂对象-->    <!--factory-method 用来指定使用哪个工厂方法-->    <bean factory-bean="carFactory" factory-method="getCar" id="car"></bean>
~~~~

创建容器获取对象测试

~~~~java
        ClassPathXmlApplicationContext app = new ClassPathXmlApplicationContext("applicationContext.xml");        //获取car对象        Car c = (Car) app.getBean("car");        System.out.println(c);
~~~~



7.1.5.2 配置静态工厂创建对象

配置文件中进行配置

~~~~xml
    <!--使用静态工厂创建Car放入容器-->    <bean class="com.zhuyl10.factory.CarStaticFactory" factory-method="getCar" id="car2"></bean>
~~~~

创建容器获取对象测试

~~~~java
        ClassPathXmlApplicationContext app = new ClassPathXmlApplicationContext("applicationContext.xml");
        //获取car对象
        Car c = (Car) app.getBean("car2");
        System.out.println(c);
~~~~

### Spring-context-注解方式

#### 1.注解开发

	为了简化配置，Spring支持使用注解代替xml配置。


​	

#### 2.Spring常用注解

##### 2.0 注解开发准备工作

	如果要使用注解开发必须要开启组件扫描，这样加了注解的类才会被识别出来。Spring才能去解析其中的注解。

```xml
<!--启动组件扫描，指定对应扫描的包路径，该包及其子包下所有的类都会被扫描，加载包含指定注解的类-->
<context:component-scan base-package="com.zhuyl10"/>
```



##### 2.1 IOC相关注解

###### 2.1.1 @Component,@Controller,@Service ,@Repository	

	上述4个注解都是加到类上的。
	
	他们都可以起到类似bean标签的作用。可以把加了该注解类的对象放入Spring容器中。
	
	实际再使用时选择任意一个都可以。但是后3个注解是语义化注解。
	
	如果是Service类要求使用@Service。
	
	如果是Dao类要求使用@Repository
	
	如果是Controllerl类(SpringMVC中会学习到)要求使用@Controller
	
	如果是其他类可以使用@Component



例如：

配置文件如下：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd">
<!--启动组件扫描，指定对应扫描的包路径，该包及其子包下所有的类都会被扫描，加载包含指定注解的类-->
    <context:component-scan base-package="com.zhuyl10"></context:component-scan>

</beans>
```

类如下：

```java
@Repository("userDao")
public class UserDaoImpl implements UserDao {

    public void show() {
        System.out.println("查询数据库，展示查询到的数据");
    }
}

```

```java
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component("phone")
public class Phone {
    private double price;
    private String name;
    private String password;
    private String path;

}

```

```java
@Service("userService")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserServiceImpl implements UserService {


    private UserDao userDao;

    private int num;

    private String str;


    public void show() {
        userDao.show();
    }
}

```



测试类如下：

```java
public class Demo {
    public static void main(String[] args) {
        //创建容器
        ClassPathXmlApplicationContext app = new ClassPathXmlApplicationContext("applicationContext.xml");
        //获取对象
        UserDao userDao = (UserDao) app.getBean("userDao");
        Phone phone = (Phone) app.getBean("phone");
        UserService userService = (UserService) app.getBean("userService");
        System.out.println(phone);
        System.out.println(userService);
        System.out.println(userDao);
    }
}
```



##### 2.2 DI相关注解

	如果一个bean已经放入Spring容器中了。那么我们可以使用下列注解实现属性注入，让Spring容器帮我们完成属性的赋值。



###### 2.2.1 @Value

	主要用于String,Integer等可以直接赋值的属性注入。不依赖setter方法，支持SpEL表达式。

例如：

```java
@Service("userService")@Data@NoArgsConstructor@AllArgsConstructorpublic class UserServiceImpl implements UserService {    private UserDao userDao;    @Value("199")    private int num;    @Value("三更草堂")    private String str;    @Value("#{19+3}")    private Integer age;    public void show() {        userDao.show();    }}
```



###### 2.2.2 @AutoWired

	Spring会给加了该注解的属性自动注入数据类型相同的对象。

例如：

```java
@Service("userService")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Value("199")
    private int num;
    @Value("三更草堂")
    private String str;

    @Value("#{19+3}")
    private Integer age;


    public void show() {
        userDao.show();
    }
}

```



	**required属性代表这个属性是否是必须的，默认值为true。如果是true的话Spring容器中如果找不到相同类型的对象完成属性注入就会出现异常。**





###### 2.2.3 @Qualifier

	如果相同类型的bean在容器中有多个时，单独使用@AutoWired就不能满足要求，这时候可以再加上@Qualifier来指定bean的名字从容器中获取bean注入。

例如：

```java
    @Autowired
    @Qualifier("userDao2")
    private UserDao userDao;

```



**注意：该直接不能单独使用。单独使用没有作用**



##### 2.3 xml配置文件相关注解

###### @Configuration

	标注在类上，表示当前类是一个配置类。我们可以用注解类来完全替换掉xml配置文件。
	
	注意：如果使用配置类替换了xml配置，spring容器要使用：AnnotationConfigApplicationContext

例如：

```java
@Configuration
public class ApplicationConfig {
}


```



###### @ComponentScan

	可以用来代替context:component-scan标签来配置组件扫描。
	
	basePackages属性来指定要扫描的包。
	
	注意要加在配置类上。

例如：

```java
@Configuration
@ComponentScan(basePackages = "com.zhuyl10")//指定要扫描的包
public class ApplicationConfig {
}


```





###### @Bean

	可以用来代替bean标签，主要用于第三方类的注入。
	
	使用：定义一个方法，在方法中创建对应的对象并且作为返回值返回。然后在方法上加上@Bean注解，注解的value属性来设置bean的名称。

例如：

```java
@Configuration
@ComponentScan(basePackages = "com.zhuyl10")
public class ApplicationConfig {

    @Bean("dataSource")
    public DruidDataSource getDataSource(){
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        druidDataSource.setUsername("root");
        druidDataSource.setUrl("jdbc:mysql://localhost:3306/mybatis_db");
        druidDataSource.setPassword("root");
        return druidDataSource;
    }

}
```



**注意事项：如果同一种类型的对象在容器中只有一个，我们可以不设置bean的名称。**

具体写法如下：

```java
@Configuration
@ComponentScan(basePackages = "com.zhuyl10")
public class ApplicationConfig {

    @Bean
    public DruidDataSource getDataSource(){
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        druidDataSource.setUsername("root");
        druidDataSource.setUrl("jdbc:mysql://localhost:3306/mybatis_db");
        druidDataSource.setPassword("root");
        return druidDataSource;
    }

}
```

获取方式如下：

```java
    public static void main(String[] args) {
        //创建注解容器
        AnnotationConfigApplicationContext app = new AnnotationConfigApplicationContext(ApplicationConfig.class);
		//根据对应类的字节码对象获取
        DataSource bean = app.getBean(DataSource.class);
        System.out.println(userService);
    }
```





###### @PropertySource

	可以用来代替context:property-placeholder，让Spring读取指定的properties文件。然后可以使用@Value来获取读取到的值。



	**使用：在配置类上加@PropertySource注解，注解的value属性来设置properties文件的路径。**
	
	**然后在配置类中定义成员变量。在成员变量上使用@Value注解来获取读到的值并给对应的成员变量赋值。**



例如：

```properties
jdbc.driver=com.mysql.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:3306/mybatis_db
jdbc.username=root
jdbc.password=root
```

读取文件并且获取值

```java
@Configuration
@ComponentScan(basePackages = "com.zhuyl10")
@PropertySource("jdbc.properties")
public class ApplicationConfig {

    @Value("${jdbc.driver}")
    private String driverClassName;
    @Value("${jdbc.url}")
    private String url;
    @Value("${jdbc.username}")
    private String username;
    @Value("${jdbc.password}")
    private String password;


    @Bean
    public DruidDataSource getDataSource(){
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName(driverClassName);
        druidDataSource.setUsername(username);
        druidDataSource.setUrl(url);
        druidDataSource.setPassword(password);
        return druidDataSource;
    }

}

```



**注意事项：使用@Value获取读到的properties文件中的值时使用的是${key},而不是#{key}。**



#### 3.如何选择

①SSM  

		自己项目中的类的IOC和DI都使用注解，对第三方jar包中的类，配置组件扫描时使用xml进行配置。

②SpringBoot

		纯注解开发



## Spring AOP

# SpringBoot中的AOP处理

## 1 理解AOP

### 1.1 什么是AOP

​		AOP（Aspect Oriented Programming），面向切面思想，是Spring的三大核心思想之一（两外两个：IOC-控制反转、DI-依赖注入）。

​		那么AOP为何那么重要呢？在我们的程序中，经常存在一些系统性的需求，比如权限校验、日志记录、统计等，这些代码会散落穿插在各个业务逻辑中，非常冗余且不利于维护。例如下面这个示意图：

![1](C:\Users\Administrator\Desktop\1.jpg)

​		有多少业务操作，就要写多少重复的校验和日志记录代码，这显然是无法接受的。当然，用面向对象的思想，我们可以把这些重复的代码抽离出来，写成公共方法，就是下面这样：

![2](C:\Users\Administrator\Desktop\2.png)

​		这样，代码冗余和可维护性的问题得到了解决，但每个业务方法中依然要依次手动调用这些公共方法，也是略显繁琐。有没有更好的方式呢？有的，那就是AOP，AOP将权限校验、日志记录等非业务代码完全提取出来，与业务代码分离，并寻找节点切入业务代码中：

![3](C:\Users\Administrator\Desktop\3.png)

### 1.2 AOP体系与概念

​		**简单去理解，其实AOP要做三类事：**

​				在哪里切入，也就是权限校验等非业务操作在哪些业务代码中执行。

​				在什么时候切入，是业务代码执行前还是执行后。

​				切入后做什么事，比如做权限校验、日志记录等。

​			因此，AOP的体系可以梳理为下图：

![](C:\Users\Administrator\Desktop\4.png)

​		**概念详解：**

​			Pointcut：切点，决定处理如权限校验、日志记录等在何处切入业务代码中（即织入切面）。切点分为execution方式和annotation方式。前者可以用路径				表达式指定哪些类织入切面，后者可以指定被哪些注解修饰的代码织入切面。

​			Advice：处理，包括处理时机和处理内容。处理内容就是要做什么事，比如校验权限和记录日志。处理时机就是在什么时机执行处理内容，分为前置处理				（即业务代码执行前）、后置处理（业务代码执行后）等。

​			Aspect：切面，即Pointcut和Advice。

​			Joint point：连接点，是程序执行的一个点。例如，一个方法的执行或者一个异常的处理。在 Spring AOP 中，一个连接点总是代表一个方法执行。

​			Weaving：织入，就是通过动态代理，在目标对象方法中执行处理内容的过程。


![](C:\Users\Administrator\Desktop\5.png)



## 2 装配AOP

使用 AOP，首先需要引入 **AOP 的依赖**。

```java
<!--Aop依赖-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-aop</artifactId>
</dependency>
```

注意：在完成了引入AOP依赖包后，一般来说并不需要去做其他配置。使用过Spring注解配置方式的人会问是否需要在程序主类中增加@EnableAspectJAutoProxy来启用，实际并不需要。

因为在AOP的默认配置属性中，spring.aop.auto属性默认是开启的，也就是说只要引入了AOP依赖后，默认已经增加了@EnableAspectJAutoProxy。

### 2.1 第一个示例：对所有的web请求做切面来记录日志

#### 2.1.1 实现一个简单的web请求入口

```java
@RestController
@RequestMapping("/demo")
public class DemoController {

    @GetMapping("/echo")
    public List<String> echo() {
        // 查询列表
        List<String> result = new ArrayList<>();
        result.add("hanf4");
        result.add("xuy7");
        result.add("xiaoh6");
        return result;
    }

    @PostMapping("/addUser")
    @PermissionAnnotation
    public JSONObject  addUser(@RequestBody JSONObject request){
        return JSON.parseObject("{\"message\":\"SUCCESS\",\"code\":200}");
    }
}
```

#### 2.1.2 定义切面类，实现web层的日志切面

要想把一个类变成切面类，需要两步：
 	1) 在类上使用 @Component 注解 把切面类加入到IOC容器中 
	 2) 在类上使用 @Aspect 注解 使之成为切面类

```java
@Component
@Aspect
public class DemoAspect {
    private Logger logger = LoggerFactory.getLogger(DemoAspect.class);
    //前置通知
    @Before("execution(public * Demo.controller.DemoController.*(..))")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 记录下请求内容
        logger.info("URL : " + request.getRequestURL().toString());
        logger.info("HTTP_METHOD : " + request.getMethod());
        logger.info("IP : " + request.getRemoteAddr());
        logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        logger.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));
    }
    // 在执行前后执行:
    @Around("execution(public * Demo.controller.DemoController.*(..))")
    public Object doLogging(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println();
        logger.info("[Around] start " + pjp.getSignature());
        Object retVal = pjp.proceed();
        logger.info("[Around] done " + pjp.getSignature());
        return retVal;
    }
}
```

观察`doBefore()`方法，我们定义了一个`@Before`注解，后面的字符串是告诉AspectJ应该在何处执行该方法，这里写的意思是：执行`DemoController`的每个`public`方法前执行`doBefore()`代码。

再观察`doLogging()`方法，我们定义了一个`@Around`注解，它和`@Before`不同，`@Around`可以决定是否执行目标方法，因此，我们在`doLogging()`内部先打印日志，再调用方法，最后打印日志后返回结果。

在`DemoAspect`类的声明处，除了用`@Component`表示它本身也是一个Bean外，我们再加上`@Aspect`注解，表示它的`@Before`标注的方法需要注入到`DemoController`的每个`public`方法执行前，`@Around`标注的方法需要注入到`DemoController`的每个`public`方法执行前后。

```java
2021-05-12 10:49:20.421  INFO 15004 --- [nio-8080-exec-2] Demo.aspect.DemoAspect                   : [Around] start List Demo.controller.DemoController.echo()
2021-05-12 10:49:20.421  INFO 15004 --- [nio-8080-exec-2] Demo.aspect.DemoAspect                   : URL : http://127.0.0.1:8080/demo/echo
2021-05-12 10:49:20.421  INFO 15004 --- [nio-8080-exec-2] Demo.aspect.DemoAspect                   : HTTP_METHOD : GET
2021-05-12 10:49:20.422  INFO 15004 --- [nio-8080-exec-2] Demo.aspect.DemoAspect                   : IP : 127.0.0.1
2021-05-12 10:49:20.423  INFO 15004 --- [nio-8080-exec-2] Demo.aspect.DemoAspect                   : CLASS_METHOD : Demo.controller.DemoController.echo
2021-05-12 10:49:20.423  INFO 15004 --- [nio-8080-exec-2] Demo.aspect.DemoAspect                   : ARGS : []
hanf4*****
2021-05-12 10:49:20.423  INFO 15004 --- [nio-8080-exec-2] Demo.aspect.DemoAspect                   : [Around] done List Demo.controller.DemoController.echo()
```

### 2.2 第二个示例：使用自定义AOP注释并对应多个切面类

#### 2.2.1 自定义一个注解PermissionsAnnotation

使用@Target、@Retention、@Documented自定义一个注解

```java
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PermissionAnnotation{
}
```

#### 2.2.2 创建两个切面类，切点设置为拦截所有标注`PermissionsAnnotation`的方法

创建第一个AOP切面类，，只要在类上加个 @Aspect 注解即可。@Aspect 注解用来描述一个切面类，定义切面类的时候需要打上这个注解。@Component 注解将该类交给 Spring 来管理。在这个类里实现id权限校验逻辑：

```java
@Component
@Aspect
@Order(1)
public class Demo1Aspect {
    @Pointcut("@annotation(Demo.customAnnotation.PermissionAnnotation)")
    private void PermissionCheck(){}
    //验证输入的id号
    @Around("PermissionCheck()")
    public Object permissionCheck(ProceedingJoinPoint pjp) throws Throwable{
        System.out.println("==========第一个切面=========");

        Object[] args = pjp.getArgs();
        Long id = ((JSONObject) args[0]).getLong("id");
        String name = ((JSONObject) args[0]).getString("name");
        System.out.println("--->"+id);
        System.out.println("--->"+name);
        if(id<0){
            System.out.println("{\"message\":\"illegal id\",\"code\":403}");
            return JSONObject.parseObject("{\"message\":\"illegal id\",\"code\":403}");
        }
        return pjp.proceed();
    }
}
```

创建第二个AOP切面类，在这个类里实现用户名权限校验：

```java
@Component
@Aspect
@Order(0)
public class Demo2Aspect {
    @Pointcut("@annotation(Demo.customAnnotation.PermissionAnnotation)")
    private void PermissionCheck(){}
    //验证输入的用户名
    @Around("PermissionCheck()")
    public Object permissionCheck(ProceedingJoinPoint pjp) throws Throwable{
        System.out.println("==========第二个切面=========");
        Object[] args = pjp.getArgs();
        Long id = ((JSONObject) args[0]).getLong("id");
        String name = ((JSONObject) args[0]).getString("name");
        System.out.println("--->"+id);
        System.out.println("--->"+name);
        if(!name.equals("hanf")){
            System.out.println("{\"message\":\"not admin\",\"code\":402}");
            return JSONObject.parseObject("{\"message\":\"not admin\",\"code\":402}");
        }
        return pjp.proceed();
    }
}
```

我们可以看到，我们在两个切面类所定义的切点都是PermissionAnnotation注释所标注的方法，当一个自定义的`AOP`注解可以对应多个切面类，那么这些**这些切面的执行顺序如何管理**？

其实，这些切面类执行顺序由`@Order`注解管理，该注解后的数字越小，所在切面类越先执行。（这里注意，越后执行不代表不执行，如果**之前的切面验证条件都通过**，那么便会按着`@Order`定义的优先级顺序执行到它）

#### 2.2.3 创建接口类，在目标方法上标注自定义注解PermissionsAnnotation

```java
@RestController
@RequestMapping("/demo")
public class DemoController {

    @GetMapping("/echo")
    public List<String> echo() {
        // 查询列表
        List<String> result = new ArrayList<>();
        result.add("hanf4");
        result.add("xuy7");
        result.add("xiaoh6");
        System.out.println("hanf4*****");
        return result;
    }

    @PostMapping("/addUser")
    @PermissionAnnotation
    public JSONObject  addUser(@RequestBody JSONObject request){
        return JSON.parseObject("{\"message\":\"SUCCESS\",\"code\":200}");
    }
}
```

这里我们还是使用了示例一中的接口类，在第二个方法上标注了自定义注释

#### 2.2.4 测试

我们构造两个参数都异常的情况：

![7](C:\Users\Administrator\Desktop\7.PNG)

响应结果，表面第二个切面类执行顺序更靠前：

![6](C:\Users\Administrator\Desktop\6.PNG)

## 3 AOP相关注解

### 3.1 @Pointcut

`@Pointcut` 注解，用来定义一个切点，即上文中所关注的某件事情的入口，切入点定义了事件触发时机。

```java
@Aspect@Componentpublic class LogAspectHandler {    /**     * 定义一个切面，拦截 com.itcodai.course09.controller 包和子包下的所有方法     */    @Pointcut("execution(* com.mutest.controller..*.*(..))")    public void pointCut() {}}
```

```java
@Component@Aspect@Order(1)public class Demo1Aspect {        /////////////////////////////////////////////    @Pointcut("@annotation(Demo.customAnnotation.PermissionAnnotation)") //    ////////////////////////////////////////////        private void PermissionCheck(){}    //验证输入的id号    @Around("PermissionCheck()")    public Object permissionCheck(ProceedingJoinPoint pjp) throws Throwable{        System.out.println("==========第一个切面=========");        Object[] args = pjp.getArgs();        Long id = ((JSONObject) args[0]).getLong("id");        String name = ((JSONObject) args[0]).getString("name");        System.out.println("--->"+id);        System.out.println("--->"+name);        if(id<0){            System.out.println("{\"message\":\"illegal id\",\"code\":403}");            return JSONObject.parseObject("{\"message\":\"illegal id\",\"code\":403}");        }        return pjp.proceed();    }}
```

​	@Pointcut 注解指定一个切点，定义需要拦截的东西，这里介绍两个常用的表达式：**一个是使用 `execution()`，另一个是使用 `annotation()`。**我们第一个示例使用的是execution()指定切点的写法，第二个示例使用的是annotation()的写法，这里推荐第二种写法，上面的代码也是用第二种写法。因为第一种方法基本能实现无差别全覆盖，即某个包下面的所有Bean的所有方法都会被这个切面方法拦截，这种非精准打击误伤面很大，因为不恰当的范围，容易导致意想不到的结果，即很多不需要AOP代理的Bean也被自动代理了，并且，后续新增的Bean，如果不清楚现有的AOP装配规则，容易被强迫装配。

​	使用AOP时，被装配的Bean最好自己能清清楚楚地知道自己被安排了。

**execution表达式：**

​	以 execution(* * com.mutest.controller..*.*(..))) 表达式为例：

​		第一个 * 号的位置：表示返回值类型，* 表示所有类型。
​		包名：表示需要拦截的包名，后面的两个句点表示当前包和当前包的所有子包，在本例中指 com.mutest.controller包、子包下所有类的方法。
​		第二个 * 号的位置：表示类名，* 表示所有类。

​		(..)：这个星号表示方法名，* 表示所有的方法，后面括弧里面表示方法的参数，两个句点表示任何参数。

**annotation() 表达式：**

​	annotation() 方式是针对某个注解来定义切点。

```java
@Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)")public void annotationPointcut() {}
```

​	然后使用该切面的话，就会切入注解是 `@PostMapping` 的所有方法。这种方式很适合处理 `@GetMapping、@PostMapping、@DeleteMapping`不同注解有各种特定处理逻辑的场景。

还有就是如上面案例所示，针对自定义注解来定义切面。

```java
@Pointcut("@annotation(com.example.demo.PermissionsAnnotation)")private void permissionCheck() {}
```

### 3.2 @Around

@Around注解用于修饰Around增强处理，Around增强处理非常强大，表现在：

  		1. @Around可以自由选择增强动作与目标方法的执行顺序，也就是说可以在增强动作前后，甚至过程中执行目标方法。这个特性的实现在于，调用					ProceedingJoinPoint参数的procedd()方法才会执行目标方法。	2. @Around可以改变执行目标方法的参数值，也可以改变执行目标方法之后的返回值。

Around增强处理有以下特点：

  		1. 当定义一个Around增强处理方法时，该方法的第一个形参必须是 ProceedingJoinPoint 类型（至少一个形参）。在增强处理方法体内，调用ProceedingJoinPoint的proceed方法才会执行目标方法：这就是@Around增强处理可以完全控制目标方法执行时机、如何执行的关键；如果程序没有调用ProceedingJoinPoint的proceed方法，则目标方法不会执行。	2. 调用ProceedingJoinPoint的proceed方法时，还可以传入一个Object[ ]对象，该数组中的值将被传入目标方法作为实参——这就是Around增强处理方法可以改变目标方法参数值的关键。这就是如果传入的Object[ ]数组长度与目标方法所需要的参数个数不相等，或者Object[ ]数组元素与目标方法所需参数的类型不匹配，程序就会出现异常。

@Around功能虽然强大，但通常需要在线程安全的环境下使用。因此，如果使用普通的Before、AfterReturning就能解决的问题，就没有必要使用Around了。如果需要目标方法执行之前和之后共享某种状态数据，则应该考虑使用Around。尤其是需要使用增强处理阻止目标的执行，或需要改变目标方法的返回值时，则只能使用Around增强处理了。

### 3.3 @Before

**`@Before` 注解指定的方法在切面切入目标方法之前执行**，可以做一些 `Log` 处理，也可以做一些信息的统计，比如获取用户的请求 `URL`以及用户的 `IP` 地址等等，这个在做个人站点的时候都能用得到，都是常用的方法。

### 3.4 @After

`@After` 注解和 `@Before` 注解相对应，指定的方法在切面切入目标方法之后执行，也可以做一些完成某方法之后的 Log 处理。

### 3.5 @AfterReturning

`@AfterReturning` 注解和 `@After` 有些类似，区别在于 `@AfterReturning` 注解可以用来捕获切入方法执行完之后的返回值，对返回值进行业务逻辑上的增强处理。

需要注意的是，在 `@AfterReturning` 注解 中，属性 `returning` 的值必须要和参数保持一致，否则会检测不到。

### 3.6 @AfterThrowing

当被切方法执行过程中抛出异常时，会进入 `@AfterThrowing` 注解的方法中执行，在该方法中可以做一些异常的处理逻辑。要注意的是 `throwing` 属性的值必须要和参数一致，否则会报错。该方法中的第二个入参即为抛出的异常。



### Spring DAO

- Spring的Dao模块是Spring框架中对应持久层的解决方式，提供了对JDBC、Hibernate等DAO层支持。
- Spring框架对JDBC进行了封装，完全抛弃了JDBC API。数据库连接、事务等也交给了Spring打点，开发者只需要使用封装好的JdbcTemplate执行SQL语句，然后得到需要的结果。

1. #### 导入包

   spring boot ：导入mysql和springJDBC的关系依赖包，在pom.xml中增加如下代码

   ```
   <dependency>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-jdbc</artifactId>
   </dependency>
    
   <dependency>
       <groupId>mysql</groupId>
       <artifactId>mysql-connector-java</artifactId>
   </dependency>
   ```

   spring：导入jar包

   - spring-jdbc-3.2.5.RELEASE.jar
   - spring-tx-3.2.5.RELEASE.jar

2. #### 数据库连接配置

   在application.yml中配置数据库连接及相关配置

   ```
   spring:
       datasource:
           url: jdbc:mysql://localhost:3306/boot_crm?serverTimezone=UTC
           username: root
           password: 123456
           driver-class-name: com.mysql.cj.jdbc.Driver
   ```

3. #### 关于Druid的相关配置

   增加依赖

   ```
   <dependency>
         <groupId>com.alibaba</groupId>
         <artifactId>druid</artifactId>
         <version>1.1.3</version>
   </dependency>
   
   ```

   在application.yml中配置数据库连接及其他相关属性

   ```
   spring:
       datasource:
   #      数据源
           type: com.alibaba.druid.pool.DruidDataSource
   #      数据库连接驱动
           driverClassName: com.mysql.jdbc.Driver
   #
           druid:
               default:  #默认数据源
   #
                 url: jdbc:mysql://localhost:3306/数据库?allowMultiQueries=true&useUnicode=true&characterEncoding=UTF-8
                 username: 账户
                 password: 密码
   #           初始化建立的物理连接数
               initial-size: 10
   #           最大连接池数量
               max-active: 1000
   #           最小连接池数量
               min-idle: 10
   #           获取连接时最大等待时间
               max-wait: 60000
   #            是否缓存preparedStatement
               pool-prepared-statements: true
                # 要启用PSCache，必须配置大于0，当大于0时，poolPreparedStatements自动触发修改为true。
               max-pool-prepared-statement-per-connection-size: 20
   #             有两个含义：1) Destroy线程会检测连接的间隔时间，如果连接空闲时间大于等于minEvictableIdleTimeMillis则关闭物理连接。
   #                        2) testWhileIdle的判断依据，详细看testWhileIdle属性的说明
               time-between-eviction-runs-millis: 60000
   #            连接保持空闲而不被驱逐的最小时间
               min-evictable-idle-time-millis: 300000
   #            用来检测连接是否有效的sql，要求是一个查询语句，常用SELECT 1 FROM DUAL。
               validation-query: SELECT 1 FROM DUAL
   #            建议配置为true，不影响性能，并且保证安全性。申请连接的时候检测，如果空闲时间大于timeBetweenEvictionRunsMillis，执行validationQuery检测连接是否有效。
               test-while-idle: true
   #            申请连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能。
               test-on-borrow: false
   #            归还连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能。
               test-on-return: false
   #            监控页面相关配置
               stat-view-servlet:
                   enabled: true
                   url-pattern: /druid/
                   #login-username: admin
                   #login-password: 123456
   #                filter相关配置
               filter:
                   stat:
                       log-slow-sql: true
                       slow-sql-millis: 1000
                       merge-sql: true
                   wall:
                       config:
                           multi-statement-allow: true
   ```

   

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

##### 本质：过滤器链

包含的过滤器：

1. WebAsyncManagerIntegrationFilter：将 Security 上下文与 Spring Web 中用于处理异步请求映射的 WebAsyncManager 进行集成。
2. SecurityContextPersistenceFilter：在每次请求处理之前将该请求相关的安全上下文信息加载到 SecurityContextHolder 中，然后在该次请求处理完成之后，将 SecurityContextHolder 中关于这次请求的信息存储到一个“仓储”中，然后将 SecurityContextHolder 中的信息清除，例如在Session中维护一个用户的安全信息就是这个过滤器处理的。
3. HeaderWriterFilter：用于将头信息加入响应中。
4. CsrfFilter：用于处理跨站请求伪造。
5. LogoutFilter：用于处理退出登录。
6. UsernamePasswordAuthenticationFilter：用于处理基于表单的登录请求，从表单中获取用户名和密码。默认情况下处理来自 /login 的请求。从表单中获取用户名和密码时，默认使用的表单 name 值为 username 和 password，这两个值可以通过设置这个过滤器的usernameParameter 和 passwordParameter 两个参数的值进行修改。
7. DefaultLoginPageGeneratingFilter：如果没有配置登录页面，那系统初始化时就会配置这个过滤器，并且用于在需要进行登录时生成一个登录表单页面。
8. BasicAuthenticationFilter：检测和处理 http basic 认证。
9. RequestCacheAwareFilter：用来处理请求的缓存。
10. SecurityContextHolderAwareRequestFilter：主要是包装请求对象request。
11. AnonymousAuthenticationFilter：检测 SecurityContextHolder 中是否存在 Authentication 对象，如果不存在为其提供一个匿名 Authentication。
12. SessionManagementFilter：管理 session 的过滤器
13. ExceptionTranslationFilter：处理 AccessDeniedException 和 AuthenticationException 异常。
14. FilterSecurityInterceptor：可以看做过滤器链的出口。
15. RememberMeAuthenticationFilter：当用户没有登录而直接访问资源时, 从 cookie 里找出用户的信息, 如果 Spring Security 能够识别出用户提供的remember me cookie, 用户将不必填写用户名和密码, 而是直接登录进入系统，该过滤器默认不开启。

本节的demo实现的功能为：

1. 注册了两个过滤器MyUsernamePasswordAuthenticationFilter、MyFilterSecurityInterceptor
2. MyUsernamePasswordAuthenticationFilter：对/login请求进行拦截，并获取请求中的用户名密码，认证成功后将认证的对象放入request属性中，认证失败直接返回。
3. MyFilterSecurityInterceptor：对白名单的路径直接进行放行，从request中获取认证对象，验证此认证对象是否能够访问当前request中的路径，如不能则重定向到登录页，反之则放行



