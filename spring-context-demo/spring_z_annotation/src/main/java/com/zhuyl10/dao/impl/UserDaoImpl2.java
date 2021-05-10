package com.zhuyl10.dao.impl;

import com.zhuyl10.dao.UserDao;
import org.springframework.stereotype.Repository;

/**
 * @author zhuyl10
 */
@Repository("userDao2")
public class UserDaoImpl2 implements UserDao {

    public void show() {
        System.out.println("查询数据库，展示查询到的数据");
    }
}
