package com.ssafy.plan.domain;

import com.ssafy.plan.persistent.entity.Plan;
import com.ssafy.plan.persistent.repository.PlanRepository;
import com.ssafy.plan.web.dto.PlanDto;
import com.ssafy.plan.web.dto.PlanDtoList;
import com.ssafy.plan.web.dto.request.CreatePlanDto;
import com.ssafy.plan.web.dto.request.UpdatePlanDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PlanService {
	
	private final PlanRepository planRepository;

	public PlanDto findPlanById(int planId) throws SQLException {
		return PlanDto.from(planRepository.findPlanById(planId));
	}

	public PlanDtoList findPlanByUserId(String userId) throws SQLException {
		List<Plan> plans = planRepository.findPlanByUserId(userId);
		return PlanDtoList.builder()
				.plans(plans.stream()
					.map(PlanDto::from)
					.collect(Collectors.toList()))
				.build();
	}

	public void createPlan(CreatePlanDto planDto, String userId) {
		System.out.println(planDto.getSidoCode());
		System.out.println(planDto.getGugunCode());
		planRepository.createPlan(planDto.toEntity(userId));
	}

	public void deletePlan(int planId) {
		planRepository.deletePlan(planId);
	}

	public void updatePlan(UpdatePlanDto updatePlanDto) throws SQLException {
		Plan plan = planRepository.findPlanById(updatePlanDto.getId());
		updatePlanDto.updateEntity(plan);
		planRepository.updatePlan(plan);
	}

	public int countPlanByUserId(String userId) {
		return planRepository.countPlanByUserId(userId);
	}
}

