package com.zhuyl10;

import com.zhuyl10.service.impl.UserServiceImpl;
import com.zhuyl10.service.UserService;

/**
 * @author zhuyl10
 */
public class Demo {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.showUser();
    }
}
