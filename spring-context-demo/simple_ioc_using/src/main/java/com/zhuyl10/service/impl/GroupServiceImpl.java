package com.zhuyl10.service.impl;

import com.zhuyl10.ClassPathXmlApplicationContext;
import com.zhuyl10.dao.UserDao;
import com.zhuyl10.service.GroupService;

/**
 * @author zhuyl10
 */
public class GroupServiceImpl implements GroupService {

    UserDao userDao  = (UserDao) new ClassPathXmlApplicationContext("beans.xml").getBean("userDao");

    @Override
    public void showUser() {
       ;
        System.out.println(userDao.getUserById(1));
    }

    @Override
    public void showUser2() {
        System.out.println(userDao.getUserById(1));
    }

    @Override
    public void showUser3() {
        System.out.println(userDao.getUserNameById(1));
    }
}
