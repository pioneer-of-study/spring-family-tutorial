package com.zhuyl10.dao.impl;

import com.zhuyl10.dao.UserDao;
import com.zhuyl10.domain.User;

/**
 * @author zhuyl10
 */
public class UserDaoImpl2 implements UserDao {
    @Override
    public String getUserNameById(Integer id) {
        return "李四2";
    }

    @Override
    public User getUserById(Integer id) {
        return new User(1,"北京2",25);
    }
}
