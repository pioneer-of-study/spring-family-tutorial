package com.cyh.event.email;

import com.cyh.event.util.ApplicationContextUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class EmailService {
    public void sendEmail(EmailEvent event){
        ApplicationContext ctx= ApplicationContextUtil.getApplicationContext();
        ctx.publishEvent(event);
    }
}
