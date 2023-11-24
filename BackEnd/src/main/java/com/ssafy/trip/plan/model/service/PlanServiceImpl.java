package com.ssafy.trip.plan.model.service;

import com.ssafy.trip.plan.exception.PlanErrorCode;
import com.ssafy.trip.plan.exception.PlanException;
import com.ssafy.trip.plan.model.PlanDto;
import com.ssafy.trip.plan.model.PlanItemDto;
import com.ssafy.trip.plan.model.mapper.PlanMapper;
import com.ssafy.trip.qna.model.QnaDto;
import com.ssafy.trip.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class PlanServiceImpl implements PlanService {
    private final PlanMapper planMapper;

    @Override
    public Map<String, Object> getPlanList(Map<String, Object> map) {
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> params = PageUtil.getStartAndListSize(map);
        int totalQnaCount = getListSize(params);
        int totalPageCount = (totalQnaCount - 1) / 10 + 1;
        List<PlanDto> list = planMapper.getPlanList(params);
        result.put("totalPgno", totalPageCount);
        result.put("planList", list);
        return result;
    }

    @Override
    public int getListSize(Map<String, Object> params) {
        return planMapper.getListSize(params);
    }

    @Override
    @Transactional
    public void setPlan(Map<String, Object> requestMap) {
        Map<String, Object> planMap = (Map<String, Object>) requestMap.get("plan");
        List<Map<String, Object>> planItemList = (List<Map<String, Object>>) requestMap.get("planItem");
        planMapper.setPlan(planMap);
        if(planMap.get("planNo") != null){
            Map<String, Object> planItemMap = new HashMap<>();
            planItemMap.put("planNo", planMap.get("planNo"));
            planItemMap.put("planItemList", planItemList);
            planMapper.setPlanItem(planItemMap);
        } else {
            throw new PlanException(PlanErrorCode.FAIL_PLAN);
        }
    }

    @Override
    public PlanDto getPlan(int planNo) {
        PlanDto planDto = planMapper.getPlan(planNo);
        if(planDto == null) throw new PlanException(PlanErrorCode.NO_PLAN);
        return planDto;
    }

    @Override
    public List<PlanItemDto> getPlanItem(int planNo) {
        List<PlanItemDto> list = planMapper.getPlanItem(planNo);
        if(list == null) throw new PlanException(PlanErrorCode.NO_PLAN);
        return list;
    }

    @Override
    @Transactional
    public Map<String, Object> getPlanAndItem(int planNo) {
        Map<String, Object> result = new HashMap<>();
        result.put("plan", getPlan(planNo));
        result.put("planItem", getPlanItem(planNo));
        return result;
    }

    @Override
    public void deletePlan(int planNo) {
        planMapper.deletePlan(planNo);
    }

    @Override
    public void deletePlanItem(int planNo) {
        planMapper.deletePlanItem(planNo);
    }

    @Override
    @Transactional
    public void deletePlanAndItem(int planNo) {
        getPlanAndItem(planNo);
        deletePlanItem(planNo);
        deletePlan(planNo);
    }
}
