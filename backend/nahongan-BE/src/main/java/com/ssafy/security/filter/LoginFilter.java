package com.ssafy.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.member.persistent.entity.Member;
import com.ssafy.member.web.dto.request.LoginRequest;
import com.ssafy.security.util.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.security.sasl.AuthenticationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    public LoginFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        setFilterProcessesUrl("/api/member/login");  // 로그인 경로를 /api/login으로 설정
    }
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        LoginRequest loginRequest = null;
        try {
            // ServletRequest의 InputStream을 String으로 변환
            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader bufferedReader = request.getReader();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            System.out.println("Request Body: " + stringBuilder.toString());
            // JSON 문자열을 LoginRequest 객체로 변환
            ObjectMapper objectMapper = new ObjectMapper();
            loginRequest = objectMapper.readValue(stringBuilder.toString(), LoginRequest.class);
        } catch (Exception e) {
            throw new RuntimeException("Authentication failed", e);
        }
        String id = loginRequest.getId();
        String password = loginRequest.getPassword();

        // 로깅 추가 (디버깅용)
        System.out.println("Attempting authentication for ID: " + id);

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(id, password, null);
        return authenticationManager.authenticate(authToken);
    };

    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) {
        Member userDetails = (Member) authentication.getPrincipal();
        String id = userDetails.getId();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();

        String role = auth.getAuthority();

        String token = jwtUtil.createJwt(userDetails, role, 60*1000L);

        response.addHeader("Authorization", "Bearer " + token);
    }

    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) {
        response.setStatus(401);
    }
}
