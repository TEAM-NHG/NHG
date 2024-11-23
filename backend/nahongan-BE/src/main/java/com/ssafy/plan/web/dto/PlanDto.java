package com.ssafy.plan.web.dto;

import com.ssafy.companion_board.persistent.entity.Comment;
import com.ssafy.companion_board.web.dto.CommentDto;
import com.ssafy.plan.persistent.entity.Plan;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PlanDto {
    int id;
    String userId;
    private String subject; // 제목
    private String content; // 내용
    private LocalDateTime registerTime; // 등록 시간
    /**
     * Entity로 변환하는 메서드
     */
    public Plan toEntity() {
        return Plan.builder()
                .id(this.id)
                .userId(this.userId)
                .subject(this.subject)
                .content(this.content)
                .registerTime(this.registerTime)
                .build();
    }

    public static PlanDto from(Plan plan) {
        return PlanDto.builder()
                .id(plan.getId())
                .userId(plan.getUserId())
                .subject(plan.getSubject())
                .content(plan.getContent())
                .registerTime(plan.getRegisterTime())
                .build();
    }
}
