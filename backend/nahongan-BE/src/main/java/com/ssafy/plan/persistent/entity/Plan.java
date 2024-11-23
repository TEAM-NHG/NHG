package com.ssafy.plan.persistent.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@Getter
public class Plan {
    int id;
    String userId;
    private String subject; // 제목
    private String content; // 내용
    private LocalDateTime registerTime; // 등록 시간

    public void update(String subject, String content) {
        this.subject = subject;
        this.content = content;
    }
}
