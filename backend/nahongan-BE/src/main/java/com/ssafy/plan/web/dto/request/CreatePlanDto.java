package com.ssafy.plan.web.dto.request;

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
    public Plan toEntity(String userId) {
        return Plan.builder()
                .userId(userId)
                .subject(this.subject)
                .content(this.content)
                .build();
    }
}
