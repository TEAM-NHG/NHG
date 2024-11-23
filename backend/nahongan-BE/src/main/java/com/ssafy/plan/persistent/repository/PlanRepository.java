package com.ssafy.plan.persistent.repository;

import com.ssafy.plan.persistent.entity.Plan;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Mapper
@Repository
public interface PlanRepository {
    Plan findPlanById(@Param("planId") int planId) throws SQLException;

    List<Plan> findPlanByUserId(@Param("userId") String userId) throws SQLException;

    void createPlan(Plan plan);

    void deletePlan(@Param("planId") int planId);

    void updatePlan(Plan plan);

    int countPlanByUserId(@Param("userId") String userId);
}
