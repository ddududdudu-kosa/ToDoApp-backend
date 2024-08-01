package com.todo.config.security.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.logging.Logger;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;

@Component
public class JWTUtil {

	private SecretKey secretKey;

	private static final Logger logger = Logger.getLogger(JWTUtil.class.getName());
	
    public JWTUtil(@Value("${spring.jwt.secret}")String secret) {
       secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
    	 // 변경된 부분: 키 생성 로직 정리
        //byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        //this.secretKey = new SecretKeySpec(keyBytes, 0, keyBytes.length, "HmacSHA256");
    }
    

    // 검증을 진행할 메소드 getUsername, getRole, isExpired
    public String getUsername(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("username", String.class);
    }

    public String getRole(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("role", String.class);
    }

    // 토큰이 만료되었는지 확인하는 메소드
    public Boolean isExpired(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
    }
    

    // 토큰 생성 메소드
    public String createJwt(String username,   String role, Long expiredMs) {

        return Jwts.builder() 
                .claim("username", username) // payload에 들어갈 username 
                .claim("role", role) // payload에 들어갈 role
                .issuedAt(new Date(System.currentTimeMillis())) // 토큰 발행 시간
                .expiration(new Date(System.currentTimeMillis() + expiredMs)) // 토큰 만료 시간
                .signWith(secretKey) // 토큰 암호화
                .compact(); // 토큰 생성
        
    }
}
