package com.example.securingweb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
  //使用官网方式配置web security
  @Override
  protected void configure(HttpSecurity http) throws Exception{
    http
        .authorizeRequests()
            .antMatchers("/","/home").permitAll()//设置哪些路径可以直接访问
            .anyRequest().authenticated()//所有请求都需要权限认证
            .and()
        .formLogin()//自定义自己编写的登陆页面
            .loginPage("/login")//登陆页面设置
            .permitAll()
            .and()
        .logout().permitAll();
  }

  //使用官网的方式自定义用户名密码
/*  @Bean
  @Override
  public UserDetailsService userDetailsService(){
    UserDetails user =
      User.withDefaultPasswordEncoder()
          .username("user")
          .password("password")
          .roles("USER")
          .build();
    return new InMemoryUserDetailsManager(user);
  }*/

  /*// 通过配置类设置用户名密码
  @Override
  protected  void configure(AuthenticationManagerBuilder auth) throws Exception{
    PasswordEncoder passwordEncoder = passwordEncoder();
    String encode = passwordEncoder.encode("1234");
    auth.inMemoryAuthentication().withUser("huq").password(encode).roles("admin");
  }*/

  //使用上面这种方式配置时，需注入PasswordEncoder，否则会报错
  @Bean
  public PasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder();
  }

  @Autowired
  UserDetailsService userDetailsService;

  // 通过数据库查询用户名密码
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception{
    auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
  }
}
