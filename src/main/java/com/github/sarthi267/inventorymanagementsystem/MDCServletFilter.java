package com.github.sarthi267.inventorymanagementsystem;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.slf4j.MDC;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
public class MDCServletFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response,
                                    FilterChain filterChain) throws IOException,ServletException {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = (authentication != null && authentication.isAuthenticated()) ? authentication.getName() : "anonymous";

            MDC.put("username", username);
            MDC.put("method", request.getMethod());
            MDC.put("uri", request.getRequestURI());

                filterChain.doFilter(request, response);
            } finally {
                MDC.clear();
            }
        }

    }


