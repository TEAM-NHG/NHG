package com.ssafy.plan.domain;

import com.ssafy.common.util.ImageUploader;
import com.ssafy.member.domain.MemberService;
import com.ssafy.member.persistent.entity.Member;
import com.ssafy.plan.persistent.entity.Plan;
import com.ssafy.plan.persistent.repository.PlanRepository;
import com.ssafy.plan.web.dto.PlanDto;
import com.ssafy.plan.web.dto.PlanDtoList;
import com.ssafy.plan.web.dto.request.CreatePlanDto;
import com.ssafy.plan.web.dto.request.UpdatePlanDto;
import com.ssafy.tripinfo.persistent.entity.Guguns;
import com.ssafy.tripinfo.persistent.repository.TripInfoRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PlanService {
	
	private final PlanRepository planRepository;
	
	private final MemberService memberService;
	
	private final TripInfoRepository tripInfoRepository;
	
	public PlanDto findPlanById(int planId) throws SQLException {
		Plan plan = planRepository.findPlanById(planId);
		String sidoName = tripInfoRepository.findSidoNameBySidoCode(plan.getSidoCode());
		String gugunName = tripInfoRepository.findGugunNameByGugunCodeAndSidoCode(plan.getSidoCode(), plan.getGugunCode());
		return PlanDto.from(plan, sidoName, gugunName);
	}

	public PlanDtoList findPlanByUserId(String userId) throws SQLException {
		List<Plan> plans = planRepository.findPlanByUserId(userId);
		
		
		return PlanDtoList.builder()
				.plans(plans.stream()
					.map((plan) -> {try {
						return PlanDto.from(plan
										,tripInfoRepository.findSidoNameBySidoCode(plan.getSidoCode())
										,tripInfoRepository.findGugunNameByGugunCodeAndSidoCode(plan.getSidoCode(), plan.getGugunCode()));
										} catch (SQLException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
										return null;
							})
					.collect(Collectors.toList()))
				.build();
	}

	public void createPlan(MultipartFile images, CreatePlanDto planDto, String userId) throws SQLException {
		//구군, 시도 이름을 기준으로 id들을 찾은 후, 넣기
		Guguns gugun = tripInfoRepository.findGugunBySidoNameAndGugunName(planDto.getSido(), planDto.getGugun());
		Plan plan = planDto.toEntity(userId, gugun.getSidoCode(), gugun.getGugunCode());
        plan.setImage(memberService.setMemberImage(images, userId));
        planRepository.createPlan(plan);
	}

	public void deletePlan(int planId) {
		planRepository.deletePlan(planId);
	}

	public void updatePlan(MultipartFile images, UpdatePlanDto updatePlanDto, String userId) throws SQLException {
		Plan plan = planRepository.findPlanById(updatePlanDto.getId());
		Guguns gugun = tripInfoRepository.findGugunBySidoNameAndGugunName(updatePlanDto.getSido(), updatePlanDto.getGugun());
		updatePlanDto.updateEntity(plan, gugun.getSidoCode(), gugun.getGugunCode());
		System.out.println(plan.getImage());
		plan.setImage(memberService.setMemberImage(images, userId));
		System.out.println(plan.getImage());
		planRepository.updatePlan(plan);
	}

	public int countPlanByUserId(String userId) {
		return planRepository.countPlanByUserId(userId);
	}
}

