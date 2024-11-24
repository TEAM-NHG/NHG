package com.ssafy.plan.web.controller;

import com.ssafy.plan.domain.PlanService;
import com.ssafy.plan.web.dto.PlanDto;
import com.ssafy.plan.web.dto.PlanDtoList;
import com.ssafy.plan.web.dto.request.CreatePlanDto;
import com.ssafy.plan.web.dto.request.UpdatePlanDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Tag(name = "유저 계획 API", description = "plan 관련 API")
@RequestMapping("/api/plan")
public class PlanController {

	private final PlanService planService;

	/**
	 * 특정 Plan 조회
	 */
	@GetMapping("/{planId}")
	public PlanDto getPlanById(@PathVariable int planId, @AuthenticationPrincipal UserDetails userDetails) throws SQLException {
		return planService.findPlanById(planId);
	}

	/**
	 * 사용자별 Plan 목록 조회
	 */
	@GetMapping
	public PlanDtoList getPlansByUserId(@AuthenticationPrincipal UserDetails userDetails) throws SQLException {
		String userId = userDetails.getUsername(); // userId 추출
		return planService.findPlanByUserId(userId);
	}

	@PostMapping
	public void createPlan(@RequestBody CreatePlanDto planDto,@AuthenticationPrincipal UserDetails userDetails) {
		planService.createPlan(planDto, userDetails.getUsername());
	}

	@PutMapping
	public void updatePlan(@RequestBody UpdatePlanDto updatePlanDto) throws SQLException {
		planService.updatePlan(updatePlanDto);
	}

	/**
	 * Plan 삭제
	 */
	@DeleteMapping("/{planId}")
	public void deletePlan(@PathVariable int planId) {
		planService.deletePlan(planId);
	}

	/**
	 * 사용자별 Plan 수 조회
	 */
	@GetMapping("/count")
	public Map<String, Integer> countPlansByUserId(@AuthenticationPrincipal UserDetails userDetails) {
		String userId = userDetails.getUsername(); // userId 추출
		int count = planService.countPlanByUserId(userId);
		return Map.of("count", count); // JSON 형식으로 반환
	}
}

