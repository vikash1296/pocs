/*
package com.inventoryservice.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LoggingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        System.out.println("LoggingFilter.doFilter started...");
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        System.out.println("Request URI: " + req.getRequestURI());
        filterChain.doFilter(servletRequest, servletResponse);
        System.out.println("LoggingFilter.doFilter completed...");
    }
}
*/
