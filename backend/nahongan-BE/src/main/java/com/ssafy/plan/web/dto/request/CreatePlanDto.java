package com.ssafy.plan.web.dto.request;

import java.util.Date;

import com.ssafy.plan.persistent.entity.Plan;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreatePlanDto {
    private String subject; // 제목
    private String content; // 내용
    private String image;
    private int sidoCode;
    private int gugunCode;
    private Date startDate;
    private Date endDate;
    
    
    public Plan toEntity(String userId) {
        return Plan.builder()
        		.userId(userId)
                .image(image)
                .sidoCode(sidoCode)
                .gugunCode(gugunCode)
                .subject(subject)
                .content(content)
                .startDate(startDate)
                .endDate(endDate)
                .build();
    }
}
