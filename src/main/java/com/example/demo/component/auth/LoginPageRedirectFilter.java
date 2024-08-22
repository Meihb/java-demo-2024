package com.example.demo.component.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class LoginPageRedirectFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 检查请求是否是登录页面的请求
        String loginPageUrl = "/login";
        if (new AntPathRequestMatcher(loginPageUrl).matches(request)) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            // 如果用户已登录，则重定向到指定页面
            if (authentication != null && authentication.isAuthenticated()) {
                String defaultPageUrl = "/";
                response.sendRedirect(defaultPageUrl);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}
