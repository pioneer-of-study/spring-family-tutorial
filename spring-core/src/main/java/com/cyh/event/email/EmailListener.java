package com.cyh.event.email;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class EmailListener {

    @EventListener
    public void EmailEventListener(EmailEvent event) {
        try {
            System.out.println("开始休眠...");
            Thread.sleep(5000L);
            System.out.println("休眠结束...");
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("EmailEventListener:"+event.getClass());
        System.out.println("注解监听到发送邮件的事件");
        System.out.println("注解需要发送的邮件地址: " + event.getAddress());
        System.out.println("注解邮件正文: " + event.getText());
    }
}
