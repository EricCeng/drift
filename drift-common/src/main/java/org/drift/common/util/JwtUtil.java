package org.drift.common.util;

import cn.hutool.core.util.IdUtil;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.MacAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.utils.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

/**
 * @author jiakui_zeng
 * @date 2024/12/27 17:35
 */
@Slf4j
public class JwtUtil {
    // private static final String SECRET = "Drift#2025!Jwt&Secret";
    private static final String SECRET_KEY = "ymyG+vK+8OUUEe1Tud/CIoyXMJ3bFQbkxtD3iFUTsFs=";
    private static final MacAlgorithm ALG = Jwts.SIG.HS256;
    private static final SecretKey KEY = ALG.key().build();
    private static final long DEFAULT_EXPIRE = 1000 * 60 * 60 * 24 * 7L;

    /**
     * 使用默认过期时间（7天），生成一个JWT
     *
     * @param userId 用户ID
     * @return token
     */
    public static String createToken(Long userId) {
        Date now = new Date();
        // 生成token
        JwtBuilder builder = Jwts.builder()
                .id(IdUtil.fastUUID())
                .issuer("DRIFT")
                .subject(String.valueOf(userId))
                .issuedAt(now)
                .expiration(new Date(now.getTime() + DEFAULT_EXPIRE))
                .signWith(generateKey());
        return builder.compact();
    }

    /**
     * 解析 token
     *
     * @param token token
     * @return Claims
     */
    public static Claims claims(String token) {
        try {
            Jws<Claims> jws = Jwts.parser()
                    .verifyWith(generateKey())
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

    public static SecretKey generateKey() {
        byte[] encodedKey = Base64.decodeBase64(SECRET_KEY);
        return new SecretKeySpec(encodedKey, 0, encodedKey.length,
                "HmacSHA256");
    }

    public static void main(String[] args) throws InterruptedException {
        String token = createToken(1881276047854284802L);
        log.info("token: {}", token);
        Thread.sleep(10000);
        Claims claims = claims(token);
        log.info("claims: {}", claims.getSubject());
        // 将密钥编码为 Base64 字符串，以便存储或传输
        String secretKeyStr = Base64.encodeBase64String(KEY.getEncoded());
        System.out.println(secretKeyStr);
    }
}
