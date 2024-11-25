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
    private String title; // 제목
    private String notes; // 내용
    private String sido;
    private String gugun;
    private Date startDate;
    private Date endDate;
    
    public void updateEntity(Plan plan, int sidoCode, int gugunCode) {
    	plan.setContent(notes);
    	plan.setSubject(title);
    	plan.setSidoCode(sidoCode);
    	plan.setGugunCode(gugunCode);
    	plan.setStartDate(startDate);
    	plan.setEndDate(endDate);
    }
}
