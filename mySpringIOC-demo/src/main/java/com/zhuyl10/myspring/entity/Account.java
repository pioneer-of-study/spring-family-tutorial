package com.zhuyl10.myspring.entity;

import com.zhuyl10.myspring.Annotation.Autowired;
import com.zhuyl10.myspring.Annotation.Component;
import com.zhuyl10.myspring.Annotation.Value;
import lombok.Data;

@Data
@Component
public class Account {
    @Value("1")
    private Integer id;
    @Value("张三")
    private String name;
    @Value("22")
    private Integer age;
    @Autowired
    private Order order;
}
