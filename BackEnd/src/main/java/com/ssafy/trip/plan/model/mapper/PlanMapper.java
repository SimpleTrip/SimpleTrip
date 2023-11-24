package com.ssafy.trip.plan.model.mapper;

import com.ssafy.trip.plan.model.PlanDto;
import com.ssafy.trip.plan.model.PlanItemDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface PlanMapper {
    List<PlanDto> getPlanList(Map<String, Object> params);
    int getListSize(Map<String, Object> params);
    void setPlan(Map<String, Object> planMap);
    void setPlanItem(Map<String, Object> planItemMap);
    PlanDto getPlan(int planNo);
    List<PlanItemDto> getPlanItem(int planNo);
    void deletePlan(int planNo);
    void deletePlanItem(int planNo);
}
