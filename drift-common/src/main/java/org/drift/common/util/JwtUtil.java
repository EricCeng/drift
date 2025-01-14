package org.drift.common.util;

import cn.hutool.core.util.IdUtil;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Date;
import java.util.Map;

/**
 * @author jiakui_zeng
 * @date 2024/12/27 17:35
 */
public class JwtUtil {
    private static final String SECRET = "Drift#2025!Jwt&Secret";
    private static final SecretKey KEY = Jwts.SIG.HS256.key()
            .random(new SecureRandom(SECRET.getBytes(StandardCharsets.UTF_8)))
            .build();
    private static final long DEFAULT_EXPIRE = 1000 * 60 * 60 * 24 * 7L;
    private static final Logger log = LoggerFactory.getLogger(JwtUtil.class);

    /**
     * 使用默认过期时间（7天），生成一个JWT
     *
     * @param userId 用户名
     * @param claims   JWT中的数据
     */
    public static String createToken(Long userId, Map<String, Object> claims) {
        return createToken(userId, claims, DEFAULT_EXPIRE);
    }

    /**
     * 生成token
     *
     * @param userId 用户名
     * @param claims   请求体数据
     * @param expire   过期时间 单位：毫秒
     * @return token
     */
    public static String createToken(Long userId, Map<String, Object> claims, Long expire) {
        JwtBuilder builder = Jwts.builder();
        Date now = new Date();
        // 生成token
        builder.id(IdUtil.fastUUID())
                .issuer("DRIFT")
                .claims(claims)
                .subject(String.valueOf(userId))
                .issuedAt(now)
                .expiration(new Date(now.getTime() + expire))
                .signWith(KEY);
        builder.header()
                .add("type", "JWT")
                .add("alg", "HS256");
        return builder.compact();
    }

    /**
     * 解析token
     *
     * @param token jwt token
     * @return Claims
     */
    public static Claims claims(String token) {
        try {
            Jws<Claims> jws = Jwts.parser()
                    .verifyWith(KEY)
                    .build()
                    .parseSignedClaims(token);
            return jws.getPayload();
        } catch (Exception e) {
            if (e instanceof ExpiredJwtException) {
                // 如果 JWT 过期会抛出ExpiredJwtException异常
                log.error("The access token is expired.");
            }
            if (e instanceof JwtException) {
                log.error("The access token is invalid.", e);
            }
            log.error("Failed to parse the access token.", e);
            throw new RuntimeException(e);
        }
    }
}
