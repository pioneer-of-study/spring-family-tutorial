# spring-family-tutorial
简介：一份由浅入深的spring全家桶教程,包含Spring Core、Spring Context、Spring AOP、Spring DAO、Spring ORM、Spring Web MVC、Spring Security 共7个模块的简单入门介绍及原理介绍，并对模块都创建了demo项目，原理介绍部分的demo项目为模块名+principle。

人员：胡强、李世宗、韩峰、崔宇航、宋兴文、许妍、朱玉麟.

-----------------

## 简单入门介绍

### Spring Core

### Spring Context
# Spring-context-xml配置方式

## 1.Spring简介

​	 Spring是一个开源框架，它由[Rod Johnson](https://baike.baidu.com/item/Rod Johnson)创建。它是为了解决企业应用开发的复杂性而创建的。 

​	 目前是JavaEE开发的灵魂框架。他可以简化JavaEE开发，可以非常方便整合其他框架，无侵入的进行功能增强。

​	 Spring的核心就是 控制反转(IoC)和面向切面(AOP) 。

## 2.IOC控制反转

### 2.1 概念

​	控制反转，之前对象的控制权在类手上，现在反转后到了Spring手上。

​	

### 2.2 入门案例

#### ①导入依赖

导入SpringIOC相关依赖

~~~~xml
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>5.1.9.RELEASE</version>
        </dependency>
~~~~

#### ②编写配置文件

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



#### ③创建容器从容器中获取对象并测试

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



### 2.3 Bean的常用属性配置

#### 2.3.1 id

​	bean的唯一标识，同一个Spring容器中不允许重复

#### 2.3.2 class

​	全类名，用于反射创建对象

#### 2.3.3 scope 

​	scope主要有两个值：singleton和prototype

​	如果设置为singleton则一个容器中只会有这个一个bean对象。默认容器创建的时候就会创建该对象。

​	如果设置为prototype则一个容器中会有多个该bean对象。每次调用getBean方法获取时都会创建一个新对象。



## 3.DI依赖注入

​	依赖注入可以理解成IoC的一种应用场景，反转的是对象间依赖关系维护权。

​	

### 3.1 set方法注入

在要注入属性的bean标签中进行配置。前提是该类有提供属性对应的set方法。

~~~~java
package com.zhuyl10.spdb;

public class Student {

    private String name;
    private int id;
    private int age;

    private Dog dog;

    public Dog getDog() {
        return dog;
    }

    public void setDog(Dog dog) {
        this.dog = dog;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", age=" + age +
                '}';
    }

    public Student() {

    }

    public Student(String name, int id, int age) {
        this.name = name;
        this.id = id;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

~~~~

~~~~xml
    <bean class="com.zhuyl10.spdb.Dog" id="dog">
        <property name="name" value="小白"></property>
        <property name="age" value="6"></property>
    </bean>

    <bean class="com.zhuyl10.spdb.Student" id="student" >
        <!--
            name属性用来指定要设置哪个属性
            value属性用来设置要设置的值
            ref属性用来给引用类型的属性设置值，可以写上Spring容器中bean的id
        -->
        <property name="name" value="东南枝"></property>
        <property name="age" value="20"></property>
        <property name="id" value="1"></property>
        <property name="dog" ref="dog"></property>
    </bean>
~~~~



### 3.2 有参构造注入

在要注入属性的bean标签中进行配置。前提是该类有提供对应的有参构造。

~~~~java
public class Student {

    private String name;
    private int id;
    private int age;

    private Dog dog;

    public Student(String name, int id, int age, Dog dog) {
        this.name = name;
        this.id = id;
        this.age = age;
        this.dog = dog;
    }
    //.....省略其他
}
~~~~

~~~~xml
    <!--使用有参构造进行注入-->
    <bean class="com.zhuyl10.spdb.Student" id="student2" >
        <constructor-arg name="name" value="自挂东南枝"></constructor-arg>
        <constructor-arg name="age" value="20"></constructor-arg>
        <constructor-arg name="id" value="30"></constructor-arg>
        <constructor-arg name="dog" ref="dog"></constructor-arg>
    </bean>
~~~~



### 3.3 复杂类型属性注入

实体类如下：

~~~~java
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private int age;
    private String name;
    private Phone phone;
    private List<String> list;
    private List<Phone> phones;
    private Set<String> set;
    private Map<String, Phone> map;
    private int[] arr;
    private Properties properties;
}
~~~~

~~~~java
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Phone {
    private double price;
    private String name;
    private String password;
    private String path;

}
~~~~



配置如下：

~~~~xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean class="com.zhuyl10.spdb.Phone" id="phone">
        <property name="price" value="3999"></property>
        <property name="name" value="黑米"></property>
        <property name="password" value="123"></property>
        <property name="path" value="qqqq"></property>
    </bean>
    
    <bean class="com.zhuyl10.spdb.User" id="user">
        <property name="age" value="10"></property>
        <property name="name" value="大队长"></property>
        <property name="phone" ref="phone"></property>
        <property name="list">
            <list>
                <value>三更</value>
                <value>西施</value>
            </list>
        </property>

        <property name="phones">
            <list>
                <ref bean="phone"></ref>
            </list>
        </property>

        <property name="set">
            <set>
                <value>setEle1</value>
                <value>setEle2</value>
            </set>
        </property>

        <property name="map">
            <map>
                <entry key="k1" value-ref="phone"></entry>
                <entry key="k2" value-ref="phone"></entry>
            </map>
        </property>

        <property name="arr">
            <array>
                <value>10</value>
                <value>11</value>
            </array>
        </property>

        <property name="properties">
            <props>
                <prop key="k1">v1</prop>
                <prop key="k2">v2</prop>
            </props>
        </property>
    </bean>
</beans>
~~~~



## 4.Lombok

### ①导入依赖

~~~~xml
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.16</version>
        </dependency>
~~~~

### ②增加注解

~~~~java
@Data //根据属性生成set，get方法
@NoArgsConstructor //生成空参构造
@AllArgsConstructor //生成全参构造
public class Phone {
    private double price;
    private String name;
    private String password;
    private String path;

}
~~~~



## 5.SPEL

​	我们可以再配置文件中使用SPEL表达式。写法如下:

~~~~xml
        <property name="age" value="#{20}"/>
        <property name="car" value="#{car}"/>
~~~~

​	注意：SPEL需要写到value属性中，不能写到ref属性。



## 6.配置文件

### 6.1 读取properties文件

​	我们可以让Spring读取properties文件中的key/value，然后使用其中的值。

#### ①设置读取properties

在Spring配置文件中加入如下标签：指定要读取的文件的路径。

~~~~xml
<context:property-placeholder location="classpath:filename.properties">
~~~~

其中的classpath表示类加载路径下。

我们也会用到如下写法：classpath:**.properties  其中的*  * 表示文件名任意。

**注意：context命名空间的引入是否正确**

#### ②使用配置文件中的值

在我们需要使用的时候可以使用${key}来表示具体的值。注意要再value属性中使用才可以。例如：

~~~~xml
<property name="propertyName" value="${key}"/>
~~~~



### 6.2 引入Spring配置文件

​	我们可以在主的配置文件中通过import标签的resource属性，引入其他的xml配置文件

~~~~xml
<import resource="classpath:applicationContext-book.xml"/>
~~~~



## 7. 低频知识点

### 7.1 bean的配置

#### 7.1.1 name属性

​	我们可以用name属性来给bean取名。例如：

~~~~xml
    <bean class="com.alibaba.druid.pool.DruidDataSource" id="dataSource" name="dataSource2,dataSource3">
        <property name="driverClassName" value="${jdbc.driver}"></property>
        <property name="url" value="${jdbc.url}"></property>
        <property name="username" value="${jdbc.username}"></property>
        <property name="password" value="${jdbc.password}"></property>
    </bean>
~~~~

​	获取的时候就可以使用这个名字来获取了

~~~~java
    public static void main(String[] args) {

        ApplicationContext app = new ClassPathXmlApplicationContext("applicationContext.xml");
        DruidDataSource dataSource = (DruidDataSource) app.getBean("dataSource3");
        System.out.println(dataSource);

    }
~~~~



#### 7.1.2 lazy-init

​	可以控制bean的创建时间，如果设置为true就是在第一次获取该对象的时候才去创建。

~~~~xml
    <bean class="com.alibaba.druid.pool.DruidDataSource" lazy-init="true"  id="dataSource" name="dataSource2,dataSource3">
        <property name="driverClassName" value="${jdbc.driver}"></property>
        <property name="url" value="${jdbc.url}"></property>
        <property name="username" value="${jdbc.username}"></property>
        <property name="password" value="${jdbc.password}"></property>
    </bean>
~~~~



#### 7.1.3 init-method

​	可以用来设置初始化方法，设置完后容器创建完对象就会自动帮我们调用对应的方法。

~~~~java
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    private String name;
    private int id;
    private int age;
	//初始化方法
    public void init(){
        System.out.println("对学生对象进行初始化操作");
    }

}

~~~~

~~~~xml
<bean class="com.zhuyl10.spdb.Student" id="student" init-method="init"></bean>
~~~~

**注意：配置的初始化方法只能是空参的。**



#### 7.1.4 destroy-method

​	可以用来设置销毁之前调用的方法，设置完后容器销毁对象前就会自动帮我们调用对应的方法。

~~~~xml
    <bean class="com.zhuyl10.spdb.Student" id="student"  destroy-method="close"></bean>
~~~~

~~~~java
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    private String name;
    private int id;
    private int age;

    public void init(){
        System.out.println("对学生对象进行初始化操作");
    }

    public void close(){
        System.out.println("对象销毁之前调用，用于释放资源");
    }
}

~~~~

**注意：配置的方法只能是空参的。**



#### 7.1.5 factory-bean&factory-method

​	当我们需要让Spring容器使用工厂类来创建对象放入Spring容器的时候可以使用factory-bean和factory-method属性。



##### 7.1.5.1 配置实例工厂创建对象

配置文件中进行配置

~~~~xml
    <!--创建实例工厂-->
    <bean class="com.zhuyl10.factory.CarFactory" id="carFactory"></bean>
    <!--使用实例工厂创建Car放入容器-->
    <!--factory-bean 用来指定使用哪个工厂对象-->
    <!--factory-method 用来指定使用哪个工厂方法-->
    <bean factory-bean="carFactory" factory-method="getCar" id="car"></bean>
~~~~

创建容器获取对象测试

~~~~java
        ClassPathXmlApplicationContext app = new ClassPathXmlApplicationContext("applicationContext.xml");
        //获取car对象
        Car c = (Car) app.getBean("car");
        System.out.println(c);
~~~~



##### 7.1.5.2 配置静态工厂创建对象

配置文件中进行配置

~~~~xml
    <!--使用静态工厂创建Car放入容器-->
    <bean class="com.zhuyl10.factory.CarStaticFactory" factory-method="getCar" id="car2"></bean>
~~~~

创建容器获取对象测试

~~~~java
        ClassPathXmlApplicationContext app = new ClassPathXmlApplicationContext("applicationContext.xml");
        //获取car对象
        Car c = (Car) app.getBean("car2");
        System.out.println(c);
~~~~

# Spring-context-注解方式

## 1.注解开发

​	为了简化配置，Spring支持使用注解代替xml配置。

​	

## 2.Spring常用注解

### 2.0 注解开发准备工作

​	如果要使用注解开发必须要开启组件扫描，这样加了注解的类才会被识别出来。Spring才能去解析其中的注解。

```xml
<!--启动组件扫描，指定对应扫描的包路径，该包及其子包下所有的类都会被扫描，加载包含指定注解的类-->
<context:component-scan base-package="com.zhuyl10"/>
```



### 2.1 IOC相关注解

#### 2.1.1 @Component,@Controller,@Service ,@Repository	

​	上述4个注解都是加到类上的。

​	他们都可以起到类似bean标签的作用。可以把加了该注解类的对象放入Spring容器中。

​	实际再使用时选择任意一个都可以。但是后3个注解是语义化注解。

​	如果是Service类要求使用@Service。

​	如果是Dao类要求使用@Repository

​	如果是Controllerl类(SpringMVC中会学习到)要求使用@Controller

​	如果是其他类可以使用@Component



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



### 2.2 DI相关注解

​	如果一个bean已经放入Spring容器中了。那么我们可以使用下列注解实现属性注入，让Spring容器帮我们完成属性的赋值。



#### 2.2.1 @Value

​	主要用于String,Integer等可以直接赋值的属性注入。不依赖setter方法，支持SpEL表达式。

例如：

```java
@Service("userService")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserServiceImpl implements UserService {
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



#### 2.2.2 @AutoWired

​	Spring会给加了该注解的属性自动注入数据类型相同的对象。

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



​	**required属性代表这个属性是否是必须的，默认值为true。如果是true的话Spring容器中如果找不到相同类型的对象完成属性注入就会出现异常。**





#### 2.2.3 @Qualifier

​	如果相同类型的bean在容器中有多个时，单独使用@AutoWired就不能满足要求，这时候可以再加上@Qualifier来指定bean的名字从容器中获取bean注入。

例如：

```java
    @Autowired
    @Qualifier("userDao2")
    private UserDao userDao;

```



**注意：该直接不能单独使用。单独使用没有作用**



### 2.3 xml配置文件相关注解

#### @Configuration

​	标注在类上，表示当前类是一个配置类。我们可以用注解类来完全替换掉xml配置文件。

​	注意：如果使用配置类替换了xml配置，spring容器要使用：AnnotationConfigApplicationContext

例如：

```java
@Configuration
public class ApplicationConfig {
}


```



#### @ComponentScan

​	可以用来代替context:component-scan标签来配置组件扫描。

​	basePackages属性来指定要扫描的包。

​	注意要加在配置类上。

例如：

```java
@Configuration
@ComponentScan(basePackages = "com.zhuyl10")//指定要扫描的包
public class ApplicationConfig {
}


```





#### @Bean

​	可以用来代替bean标签，主要用于第三方类的注入。

​	使用：定义一个方法，在方法中创建对应的对象并且作为返回值返回。然后在方法上加上@Bean注解，注解的value属性来设置bean的名称。

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





#### @PropertySource

​	可以用来代替context:property-placeholder，让Spring读取指定的properties文件。然后可以使用@Value来获取读取到的值。



​	**使用：在配置类上加@PropertySource注解，注解的value属性来设置properties文件的路径。**

​	**然后在配置类中定义成员变量。在成员变量上使用@Value注解来获取读到的值并给对应的成员变量赋值。**



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



## 3.如何选择

①SSM  

​		自己项目中的类的IOC和DI都使用注解，对第三方jar包中的类，配置组件扫描时使用xml进行配置。

②SpringBoot

​		纯注解开发

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

   最后，运行程序，验证效果。对应完整的入门搭建demo位于本项目的spring-security包中。

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