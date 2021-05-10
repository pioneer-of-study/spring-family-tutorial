package com.zhuyl10;

import com.zhuyl10.service.UserService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author zhuyl10
 */
public class Demo {

    public static void main(String[] args) {

        //获取UserService的实现类，调用其show方法

        //创建容器
        ClassPathXmlApplicationContext app = new ClassPathXmlApplicationContext("applicationContext.xml");

        //获取对象
        UserService userService = (UserService) app.getBean("userService");


        userService.show();
    }
}
