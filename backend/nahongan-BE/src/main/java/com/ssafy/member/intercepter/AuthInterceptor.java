package com.ssafy.member.intercepter;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {
//	@Override
//	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
//		if (CorsUtils.isPreFlightRequest(request)) {
//			return true;
//		}
//
//		if (isGetMethod(request)) {
//			return true;
//		}
//
//		return true;
//	}
//
//	private boolean isGetMethod(HttpServletRequest request) {
//		return request.getMethod().equalsIgnoreCase("GET");
//	}
}