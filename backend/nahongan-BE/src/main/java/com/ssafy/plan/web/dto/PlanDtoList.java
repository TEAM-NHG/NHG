package com.ssafy.plan.web.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PlanDtoList {
    List<PlanDto> plans;
}
