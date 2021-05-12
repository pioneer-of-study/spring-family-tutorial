# spring-core-tutorial

## 简单介绍

### Spring Core入门

> > 该部分参考：spring-conrext-demo当中的IOC，DI，注解开发


## Spring Context拓展应用简单介绍

### 1.国际化支持

#### 简介
> 国际化的英文为Internationalization，简称为I18n(英文单词 internationalization的首末字符i和n，18为中间的字符数)。 
> 国际化的操作就是指一个程序可以同时适应多门语言，
> 即：如果现在程序的使用者是中国人，则会以中文为显示文字，
> 如果现在程序的使用者是美国人，则会以英语为显示的文字，也就是说可以通过国际化操作，让一个程序适应各个国家的语言要求。

#### 原理

> 程序根据不同的语言环境找到不同的资源文件，之后从资源文件中取出
> 内容，资源文件中的内容是以key-value的形式保存的，所以在读取的时候通过其key找到对应的value
#### Demo
1、applicationContext.xml
```
    <!-- 国际化demo -->
    <bean id="messageSource" class="org.springframework.context.support.ResourceBundleMessageSource">
        <property name="basenames">
            <list>
                <value>message</value>
            </list>
        </property>
    </bean>
```
> 这里定义了一个MessageSource的实现类ResourceBundleMessageSource用户提供国际化功能，这里的id以messageSource命名的原因可以查看AbstractApplicationContext的源代码，里面定义了一个messageSource的属性，并提供了set方法，也就是Spring在初始化时将Spring配置文件（applicationContext.xml）中id为messageSource的bean注入到ApplicationContext中，这样就可以使用ApplicationContext提供的国际化功能了

2、message_en_US.properties
```
k1=welcome\uFF1A {0}
```
3、message_zh_CN.properties
```
k1=\u6b22\u8fce\u4f60\uff0c{0}
```
> 国际化资源文件有一定的命名规范，只有符合命名规范 的国际化资源
> 文件才能正确的被Spring读取，国际化资源问及爱你命名规范遵循：${filename}_${languagename}_${countryname}，其中${}是需要替代的内容，下划线是必需的分隔符

4、测试
```
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Locale;

public class I18NTest {

    @Test
    public void Test() {
        //Locale defaultLocale = Locale.getDefault();
        Locale defaultLocale = Locale.US;
        System.out.println("country="+ defaultLocale.getCountry());
        System.out.println("language="+ defaultLocale.getLanguage());

        Object[] arg = new Object[] { "Yuang"};
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        String msg = ctx.getMessage("k1", arg, defaultLocale);

        System.out.println(msg);
    }
}
```
显示结果
```
ountry=CN
language=zh
欢迎你，Yuang
welcome：Yuang
```

### 2.事件传递

> ApplicationContext事件机制是观察者设计模式（订阅/发布模式）的实现，通过ApplicationEvent类和ApplicationListener接口，可以实现ApplicationContext事件处理。
> 如果容器中有一个ApplicationListener Bean，每当ApplicationContext发布ApplicationEvent时，ApplicationListener Bean将自动被触发。

    Spring的事件框架有如下两个重要的成员：

- ApplicationEvent：容器事件，必须由ApplicationContext发布
- ApplicationListener：监听器，可由容器中的任何监听器Bean担任 

&nbsp;&nbsp;实际上，Spring的事件机制与所有事件机制都基本相似，它们都需要事件源、事件和事件监听器组成。只是此处的事件源是ApplicationContext，且事件必须由Java程序显式触发。下面的程序将演示Spring容器的事件机制。
 
1、 EmailEvent
> 定义了一个EmailEvent类，其对象就是一个Spring容器事件。该类继承了ApplicationEvent类，除此之外，它就是一个普通的Java类。
```
package com.cyh.event.email;

import lombok.Data;
import org.springframework.context.ApplicationEvent;

@Data
public class EmailEvent extends ApplicationEvent {

    private static final long serialVersionUID = 8890656093518139995L;
    private String address;
    private String text;

    public EmailEvent(Object source) {
        super(source);
    }
}
```
2、EmailListener
使用@EventListener注解实现
```
package com.cyh.event.email;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class EmailListener {

    @EventListener
    public void EmailEventListener(EmailEvent event) {
        try {
            System.out.println("开始休眠...");
            Thread.sleep(5000L);
            System.out.println("休眠结束...");
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("EmailEventListener:"+event.getClass());
        System.out.println("注解监听到发送邮件的事件");
        System.out.println("注解需要发送的邮件地址: " + event.getAddress());
        System.out.println("注解邮件正文: " + event.getText());
    }
}
```
&nbsp;&nbsp;EmailEventListener方法，可以传入ApplicationEvent参数，监听所有的事件，
本例只传入EmailEvent参数，只监听EmailEvent事件。然后将监听器配置在Spring的容器中。

3、EmailService
> 当系统创建Spring容器、加载Spring容器时会自动触发容器事件，容器事件监听器可以监听到这些事件。
> 除此之外，程序也可以调用ApplicationContext的publishEvent()方法来主动触发一个容器事件

```
package com.cyh.event.email;

import com.cyh.event.util.ApplicationContextUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class EmailService {
    public void sendEmail(EmailEvent event){
        ApplicationContext ctx= ApplicationContextUtil.getApplicationContext();
        ctx.publishEvent(event);
    }
}
```
4、ApplicationContextUtil
> 如果Bean想发布事件，则Bean必须获得其容器的引用。如果程序中没有直接获取容器的引用，
> 则应该让Bean实现ApplicationContextAware或者BeanFactoryAware接口，从而可以获得容器的引用
```
package com.cyh.event.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextUtil implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (ApplicationContextUtil.applicationContext == null) {
            ApplicationContextUtil.applicationContext = applicationContext;
        }
    }
}
```

5、EmailController
入口
```
package com.cyh.event.controller;

import com.cyh.event.email.EmailEvent;
import com.cyh.event.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

    @Autowired
    private EmailService emailService;

    @RequestMapping("/sendEmail")
    public void sendEmail() {
        EmailEvent emailEvent = new EmailEvent("source");
        emailEvent.setAddress("my address");
        emailEvent.setText("hello world");
        emailService.sendEmail(emailEvent);
    }
}
```
验证

访问路径

> http://127.0.0.1:8080/sendEmail

显示结果
```
开始休眠...
休眠结束...
EmailEventListener:class com.cyh.event.email.EmailEvent
注解监听到发送邮件的事件
注解需要发送的邮件地址: my address
注解邮件正文: hello world
```
#### Spring提供如下几个内置事件：
* ContextRefreshedEvent：ApplicationContext容器初始化或刷新时触发该事件。
  此处的初始化是指：所有的Bean被成功装载，后处理Bean被检测并激活，
  所有Singleton Bean 被预实例化，ApplicationContext容器已就绪可用

* ContextStartedEvent：当使用ConfigurableApplicationContext(ApplicationContext的子接口）
  接口的start()方法启动ApplicationContext容器时触发该事件。
  容器管理声明周期的Bean实例将获得一个指定的启动信号，这在经常需要停止后重新启动的场合比较常见

* ContextClosedEvent：当使用ConfigurableApplicationContext接口的close()方法
  关闭ApplicationContext时触发该事件

* ContextStoppedEvent：当使用ConfigurableApplicationContext接口的stop()方法
  使ApplicationContext容器停止时触发该事件。
  此处的停止，意味着容器管理生命周期的Bean实例将获得一个指定的停止信号，
  被停止的Spring容器可再次调用start()方法重新启动

* RequestHandledEvent：Web相关事件，只能应用于使用DispatcherServlet的Web应用。
  在使用Spring作为前端的MVC控制器时，当Spring处理用户请求结束后，系统会自动触发该事件。

