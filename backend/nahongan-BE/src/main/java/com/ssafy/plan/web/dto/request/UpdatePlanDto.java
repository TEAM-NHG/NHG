package com.ssafy.plan.web.dto.request;

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
}
