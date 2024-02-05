package com.example.demo.utils;

import java.util.Date;

import javax.crypto.SecretKey;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JwtUtil {
    // 过期时间： 一天
    public static final long EXPIRE = 1000 * 60 * 60 * 24;
    // 加密密钥
    public static final String APP_SECRET = "bbtestllbbtestllbbtestllbbtestllbbtestllbbtestllbbtestllbbtestllbbtestll";

    public static final SecretKey KEY = Keys.hmacShaKeyFor(APP_SECRET.getBytes());
    /**
     * 创建 token
     * @param id
     * @param username
     * @return
     */
    public static String createJwtToken(String uuid) {

        String jwtToken = Jwts.builder()
                .header()
                .add("typ", "JWT")
                .add("alg", "HS256")
                .and()
                .subject("authToken")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRE))
                .claim("uuid", uuid)
                .signWith(KEY, Jwts.SIG.HS256)
                .compact();

        return jwtToken;
    }

    /**
     * 验证token
     * @param jwtToken
     * @return
     */
    public static boolean checkToken(String jwtToken) {
        
        try {
            Jwts.parser().verifyWith(KEY).build().parseSignedClaims(jwtToken);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
