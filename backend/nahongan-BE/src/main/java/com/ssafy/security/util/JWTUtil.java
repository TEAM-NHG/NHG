package com.ssafy.security.util;

import com.ssafy.member.persistent.entity.Member;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JWTUtil {
    private final SecretKey secretKey;

    public JWTUtil(@Value("${spring.jwt.secret}") String secret) {
        // 시크릿 키 생성 시 예외 처리 추가
        try {
            this.secretKey = new SecretKeySpec(
                    secret.getBytes(StandardCharsets.UTF_8),
                    Jwts.SIG.HS256.key().build().getAlgorithm()
            );
        } catch (Exception e) {
            throw new IllegalStateException("Failed to create JWT secret key", e);
        }
    }

    // Claims를 가져오는 공통 메서드
    private Claims getClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException | UnsupportedJwtException | SecurityException | MalformedJwtException |
                 IllegalArgumentException e) {
            throw e;
        }
    }

    // 각 claim을 가져오는 메서드들
    public String getId(String token) {
        return getClaimOrDefault(token, "id", String.class);
    }

    public String getRole(String token) {
        return getClaimOrDefault(token, "role", String.class);
    }

    public String getEmail(String token) {
        return getClaimOrDefault(token, "email", String.class);
    }

    public String getPhone(String token) {
        return getClaimOrDefault(token, "phone", String.class);
    }

    public String getImage(String token) {
        return getClaimOrDefault(token, "image", String.class);
    }

    public String getNickname(String token) {
        return getClaimOrDefault(token, "nickname", String.class);
    }

    // Null-safe claim 조회 메서드
    private <T> T getClaimOrDefault(String token, String claimName, Class<T> type) {
        Claims claims = getClaims(token);
        return claims.get(claimName, type);
    }

    public Boolean isExpired(String token) {
        try {
            return getClaims(token).getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        }
    }

    public String createJwt(Member member, String role, Long expiredMs) {
        Date now = new Date();
        try {
            return Jwts.builder()
                    .claim("id", member.getId())
                    .claim("role", role)
                    .claim("nickname", member.getNickname())
                    .claim("image", member.getImg())
                    .claim("email", member.getEmail())
                    .claim("phone", member.getPhone())
                    .issuedAt(now)
                    .expiration(new Date(now.getTime() + expiredMs))
                    .signWith(secretKey)
                    .compact();
        } catch (Exception e) {
            throw new RuntimeException("Failed to create JWT", e);
        }
    }

    // 토큰 유효성 검증 메서드 추가
    public boolean validateToken(String token) {
        try {
            getClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Member toEntity(String token) {
        return Member.builder()
                .id(this.getId(token))
                .nickname(this.getNickname(token))
                .img(this.getImage(token))
                .email(this.getEmail(token))
                .role(this.getRole(token))
                .phone(this.getPhone(token))
                .build();
    }
}
