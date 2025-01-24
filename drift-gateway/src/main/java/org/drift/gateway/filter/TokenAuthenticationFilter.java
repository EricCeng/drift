package org.drift.gateway.filter;

import cn.hutool.core.util.StrUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.drift.common.api.ResultCode;
import org.drift.common.exception.ApiException;
import org.drift.common.util.JwtUtil;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

/**
 * @author jiakui_zeng
 * @date 2025/1/8 16:26
 */
@Component
@Slf4j
public class TokenAuthenticationFilter implements GlobalFilter, Ordered {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String AUTHORIZATION_BEARER = "Bearer ";
    private static final List<String> IGNORE_URIS = Arrays.asList("/drift/auth/register", "/drift/auth/login");
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        log.info("[Path]: {}", path);
        if (IGNORE_URIS.contains(path)) {
            return chain.filter(exchange);
        }
        String authorization = exchange.getRequest().getHeaders().getFirst(AUTHORIZATION_HEADER);
        log.info("[Authorization]: {}", authorization);
        if (StringUtils.hasLength(authorization)) {
            // 解析 token，将其中的 user id 写入到请求 header 中
            // 由后续微服务的过滤器来将 user id 写入到 thread local 中
            String userId;
            try {
                String token = StrUtil.subAfter(authorization, AUTHORIZATION_BEARER, true);
                Claims claims = JwtUtil.claims(token);
                userId = claims.getSubject();
            } catch (Exception e) {
                return Mono.error(new ApiException(ResultCode.INVALID_TOKEN));
            }
            if (StringUtils.hasLength(userId)) {
                exchange.getRequest().mutate()
                        .header("USER-ID", userId)
                        .build();
            }
            return chain.filter(exchange);
        }
        return Mono.error(new ApiException(ResultCode.UNAUTHORIZED));
    }

    @Override
    public int getOrder() {
        return -100;
    }
}
