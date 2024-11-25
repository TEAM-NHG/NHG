package com.ssafy.plan.web.dto;

import com.ssafy.companion_board.persistent.entity.Comment;
import com.ssafy.companion_board.web.dto.CommentDto;
import com.ssafy.plan.persistent.entity.Plan;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PlanDto {
	int id;
    String userId;
    private String image;
    private String title; // 제목
    private String notes; // 내용
    private String sido;
    private String gugun;
    private Date startDate;
    private Date endDate;
    private LocalDateTime registerTime; // 등록 시간
    
    public static PlanDto from(Plan plan, String sidoName, String gugunName) {
    	return PlanDto.builder()
    			.id(plan.getId())
    			.userId(plan.getUserId())
    			.image(plan.getImage())
    			.title(plan.getSubject())
    			.notes(plan.getContent())
    			.sido(sidoName)
    			.gugun(gugunName)
    			.startDate(plan.getStartDate())
    			.endDate(plan.getEndDate())
    			.registerTime(plan.getRegisterTime())
    			.build();
    }
}
