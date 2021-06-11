package com.example.securingweb;

import org.springframework.security.core.Authentication;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class MyFilterSecurityInterceptor implements javax.servlet.Filter {

  private static List<String> whiltList = Arrays.asList("/home");
  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
    HttpServletRequest request = (HttpServletRequest) servletRequest;
    HttpServletResponse response = (HttpServletResponse) servletResponse;
    if(whiltList.contains(request.getRequestURI())){
      filterChain.doFilter(request,response);
      return;
    }
    Authentication authentication = (Authentication) request.getAttribute("authentication");
    if(authentication==null || !authentication.getAuthorities().contains(request.getRequestURI())){
      request.getRequestDispatcher("/login").forward(request,response);
      return;
    }
    filterChain.doFilter(request,response);
  }
}
