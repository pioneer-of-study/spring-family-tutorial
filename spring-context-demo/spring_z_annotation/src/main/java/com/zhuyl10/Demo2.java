package com.zhuyl10;

import com.zhuyl10.config.ApplicationConfig;
import com.zhuyl10.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.sql.DataSource;

/**
 * @author zhuyl10
 */
public class Demo2 {
    public static void main(String[] args) {
        //创建注解容器
        AnnotationConfigApplicationContext app = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        UserService userService = (UserService) app.getBean("userService");

//        DataSource dataSource = (DataSource) app.getBean("dataSource");

        DataSource bean = app.getBean(DataSource.class);
        System.out.println(userService);
    }
}
