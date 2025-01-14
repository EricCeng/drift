package org.drift.common.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.drift.common.context.UserContextHolder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * @author jiakui_zeng
 * @date 2025/1/13 11:11
 */
@Component
public class CommonFilter extends OncePerRequestFilter {
    @Value("${spring.application.name}")
    private String appName;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if ("drift-gateway-service".equals(appName)) {
            // 网关无需执行该 filter，请求转发至业务服务后，由业务服务执行该 filter
            return;
        }
        String userId = request.getHeader("USER-ID");
        if (StringUtils.hasLength(userId)) {
            UserContextHolder.setUserContext(Long.valueOf(userId));
        }
        try {
            filterChain.doFilter(request, response);
        } finally {
            UserContextHolder.clear();
        }
    }
}
