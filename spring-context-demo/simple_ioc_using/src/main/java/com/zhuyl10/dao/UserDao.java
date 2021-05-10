package com.zhuyl10.dao;

import com.zhuyl10.domain.User;

/**
 * @author zhuyl10
 */
public interface UserDao {

    String getUserNameById(Integer id);

    User getUserById(Integer id);

}
