package com.example.securingweb;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class MyUsernamePasswordAuthenticationFilter implements javax.servlet.Filter {

  public static final String USERNAME = "username";

  public static final String PASSWORD = "password";

  private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher("/login",
    "GET");

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
    HttpServletRequest request = (HttpServletRequest) servletRequest;
    HttpServletResponse response = (HttpServletResponse) servletResponse;
    if (DEFAULT_ANT_PATH_REQUEST_MATCHER.matches(request)) {
      String username = request.getParameter(USERNAME);
      String password = request.getParameter(PASSWORD);
      Authentication authentication = authentication(username, password);
      if(authentication==null){
        return;
      }
      successfulAuthentication(request,response,filterChain,authentication);
      return;
    }
    filterChain.doFilter(request,response);
  }

  private void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, Authentication authentication) {
    // spring 将authentication对象放入上下文中,为了方便将其放入request属性中
    // SecurityContextHolder.getContext().setAuthentication(authentication);
    request.setAttribute("authentication",authentication);
    try {
      request.getRequestDispatcher("/hello").forward(request,response);
      return;
    } catch (ServletException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    } catch (IOException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }

  Authentication authentication(String username, String password) {
    User user = getUserByUsername(username);
    if(password.equals(user.getPassword())){
      return new UsernamePasswordAuthenticationToken(username,"",user.getAuthorities());
    }
    return null;
  }

  private User getUserByUsername(String username) {
    //省去从数据库取对象，直接返回
    Collection<GrantedAuthority> list = new ArrayList<>();
    list.add(new SimpleGrantedAuthority("/hello"));
    return new User(username,"123",list);
  }

}
