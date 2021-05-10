package com.zhuyl10.service.impl;

import com.zhuyl10.dao.UserDao;
import com.zhuyl10.service.UserService;

/**
 * @author zhuyl10
 */
public class UserServiceImpl implements UserService {


    private UserDao userDao;

    private int num;

    private String str;

    public UserServiceImpl(UserDao userDao, int num, String str) {
        this.userDao = userDao;
        this.num = num;
        this.str = str;
    }
//    public void setNum(int num) {
//        this.num = num;
//    }

    public UserServiceImpl() {
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    public void show() {
        userDao.show();
    }
}
