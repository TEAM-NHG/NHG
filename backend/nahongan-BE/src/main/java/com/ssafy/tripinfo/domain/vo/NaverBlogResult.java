package com.ssafy.tripinfo.domain.vo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.ssafy.tripinfo.persistent.entity.BlogArticle;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class NaverBlogResult {
    private String lastBuildDate;
    private int total;
    private int start;
    private int display;
    private List<Item> items;

    @Setter
    @Getter
    private static class Item {
        String title;
        String link;
        String description;
        String bloggername;
        String bloggerlink;
        String postdate;
    }

    public List<BlogArticle> toEntities() {
        // SimpleDateFormat 정의
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyyMMdd");

        return this.getItems().stream()
                .map(item -> {
                    Date createdDate = null;
                    try {
                        // 날짜 변환
                        createdDate = inputFormat.parse(item.getPostdate());
                    } catch (ParseException e) {
                        e.fillInStackTrace(); // 로그 출력 또는 예외 처리
                    }

                    return BlogArticle.builder()
                            .title(item.getTitle())
                            .description(item.getDescription())
                            .link(item.getLink())
                            .bloggerlink(item.getBloggerlink())
                            .bloggername(item.getBloggername())
                            .createdDate(createdDate) // Date 타입 사용
                            .build();
                })
                .collect(Collectors.toList());
    }

}