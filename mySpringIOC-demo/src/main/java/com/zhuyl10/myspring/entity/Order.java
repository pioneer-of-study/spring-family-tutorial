package com.zhuyl10.myspring.entity;

import com.zhuyl10.myspring.Annotation.Component;
import com.zhuyl10.myspring.Annotation.Value;
import lombok.Data;

@Data
@Component("myOrder")
public class Order {
    @Value("xxx123")
    private String orderId;
    @Value("1000.5")
    private Float price;
}
