package com.zhuyl10;

import com.zhuyl10.domain.Student;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author zhuyl10
 */
public class Demo {

    public static void main(String[] args) {

        ClassPathXmlApplicationContext app = new ClassPathXmlApplicationContext("applicationContext.xml");
//        DruidDataSource dataSource = (DruidDataSource) app.getBean("dataSource3");
        Student student = (Student) app.getBean("student");
        System.out.println(student);
        app.close();

    }

}
