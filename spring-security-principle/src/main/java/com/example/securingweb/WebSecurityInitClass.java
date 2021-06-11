package com.example.securingweb;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebSecurityInitClass {
  @Bean
  public FilterRegistrationBean usernamePasswordFilter(){
      FilterRegistrationBean registrationBean = new FilterRegistrationBean();
      registrationBean.setName("MyUsernamePasswordAuthenticationFilter");
      registrationBean.setFilter(new MyUsernamePasswordAuthenticationFilter());
      registrationBean.setOrder(1);
      return registrationBean;
  }

  @Bean
  public FilterRegistrationBean filterSecurityInterceptor(){
    FilterRegistrationBean registrationBean = new FilterRegistrationBean();
    registrationBean.setName("MyFilterSecurityInterceptor");
    registrationBean.setFilter(new MyFilterSecurityInterceptor());
    registrationBean.setOrder(2);
    return registrationBean;
  }



}
