package com.ssafy.tripinfo.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssafy.tripinfo.domain.vo.NaverBlogResult;
import com.ssafy.tripinfo.persistent.entity.BlogArticle;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;

@Service
@RequiredArgsConstructor
public class NaverApiService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${naver.client-id}")
    private String clientId;

    @Value("${naver.client-secret}")
    private String clientSecret;

    private ResponseEntity<String> requestApi(URI uri) {
        RequestEntity<Void> requestEntity = RequestEntity
                .get(uri)
                .header("X-Naver-Client-Id", clientId)
                .header("X-Naver-Client-Secret", clientSecret)
                .build();
        return restTemplate.exchange(requestEntity, String.class);
    }

    public List<BlogArticle> searchBlog(String query, int display, int start, String sort) {
        URI uri = UriComponentsBuilder
                .fromUriString("https://openapi.naver.com/v1/search/blog.json")
                .queryParam("query", query)
                .queryParam("display", display)
                .queryParam("start", start)
                .queryParam("sort", sort)
                .encode()
                .build()
                .toUri();

        return responseParser(requestApi(uri));
    }

    private List<BlogArticle> responseParser(ResponseEntity<String> response) {
        NaverBlogResult result = null;
        try {
            result = objectMapper.readValue(response.getBody(), NaverBlogResult.class);
        } catch (JsonProcessingException e) {
            e.fillInStackTrace();
        }
        return Objects.requireNonNull(result).toEntities();
    }
}
