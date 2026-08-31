package com.inventoryservice.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class LoggingInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             @NonNull HttpServletResponse response,
            @NonNull Object handler) {

        System.out.println("PreHandle executed for request URI: " + request.getRequestURI());
        return true;
    }

    @Override
    public void postHandle(@NonNull HttpServletRequest request,
                           @NonNull HttpServletResponse response,
                           @NonNull Object handler, @Nullable ModelAndView modelAndView) throws Exception {

        System.out.println("Post handler executed for request URI: " + request.getRequestURI());
        //modelAndView.addObject("requestURI", request.getRequestURI());
        System.out.println("Post handler executed for request URI: " + request.getRequestURI());
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                @NonNull HttpServletResponse response,
                                @NonNull Object handler,
                                Exception ex) {

        System.out.println("After Completion executes for Request URI: " + request.getRequestURI());
    }
}
