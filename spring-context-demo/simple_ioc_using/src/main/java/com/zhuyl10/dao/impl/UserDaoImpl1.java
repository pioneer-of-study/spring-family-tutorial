package com.zhuyl10.dao.impl;

import com.zhuyl10.dao.UserDao;
import com.zhuyl10.domain.User;

/**
 * @author zhuyl10
 */
public class UserDaoImpl1 implements UserDao {
    @Override
    public String getUserNameById(Integer id) {
        return "ζε1";
    }

    @Override
    public User getUserById(Integer id) {
        return new User(1,"εδΊ¬1",27);
    }
}
