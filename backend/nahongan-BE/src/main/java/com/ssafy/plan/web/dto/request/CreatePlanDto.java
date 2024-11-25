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
    private String title; // 제목
    private String notes; // 내용
    private String sido;
    private String gugun;
    private Date startDate;
    private Date endDate;
    
    public Plan toEntity(String userId, int sidoCode, int gugunCode) {
    	return Plan.builder()
    			.subject(title)
    			.content(notes)
    			.sidoCode(sidoCode)
    			.gugunCode(gugunCode)
    			.userId(userId)
    			.startDate(startDate)
    			.endDate(endDate)
    			.build();
    }
}
