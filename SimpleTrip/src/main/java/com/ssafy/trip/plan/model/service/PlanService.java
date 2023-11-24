package com.ssafy.trip.plan.model.service;

import com.ssafy.trip.plan.model.PlanDto;
import com.ssafy.trip.plan.model.PlanItemDto;

import java.util.List;
import java.util.Map;

public interface PlanService {
    Map<String, Object> getPlanList(Map<String, Object> params);
    int getListSize(Map<String, Object> params);
    void setPlan(Map<String, Object> requestMap);
    PlanDto getPlan(int planNo);
    List<PlanItemDto> getPlanItem(int planNo);
    Map<String, Object> getPlanAndItem(int planNo);
    void deletePlan(int planNo);
    void deletePlanItem(int planNo);
    void deletePlanAndItem(int planNo);
}
