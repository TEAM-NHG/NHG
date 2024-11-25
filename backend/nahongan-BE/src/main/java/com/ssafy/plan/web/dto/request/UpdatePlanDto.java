package com.ssafy.plan.web.dto.request;

import java.util.Date;

import com.ssafy.plan.persistent.entity.Plan;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdatePlanDto {
    private int id;
    private String subject; // 제목
    private String content; // 내용
    private int sidoCode;
    private int gugunCode;
    private Date startDate;
    private Date endDate;
    
    public void updateEntity(Plan plan) {
    	plan.setContent(content);
    	plan.setSubject(subject);
    	plan.setSidoCode(sidoCode);
    	plan.setGugunCode(gugunCode);
    	plan.setStartDate(startDate);
    	plan.setEndDate(endDate);
    }
}
