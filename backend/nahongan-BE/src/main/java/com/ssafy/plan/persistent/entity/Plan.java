package com.ssafy.plan.persistent.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class Plan {
    int id;
    String userId;
    private String image;
    private int sidoCode;
    private int gugunCode;
    private String subject; // 제목
    private String content; // 내용
    private Date startDate;
    private Date endDate;
    private LocalDateTime registerTime; // 등록 시간
}
