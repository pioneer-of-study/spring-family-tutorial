package com.zhuyl10.service.impl;

import com.zhuyl10.dao.UserDao;
import com.zhuyl10.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
/**
 * @author zhuyl10
 */
@Service("userService")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    @Qualifier("userDao2")
    private UserDao userDao;

    @Value("199")
    private int num;
    @Value("三更草堂")
    private String str;

    @Value("#{19+3}")
    private Integer age;


    public void show() {
        userDao.show();
    }
}
