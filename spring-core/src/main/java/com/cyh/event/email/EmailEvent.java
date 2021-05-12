package com.cyh.event.email;

import lombok.Data;
import org.springframework.context.ApplicationEvent;

@Data
public class EmailEvent extends ApplicationEvent {

    private static final long serialVersionUID = 8890656093518139995L;
    private String address;
    private String text;

    public EmailEvent(Object source) {
        super(source);
    }
}
