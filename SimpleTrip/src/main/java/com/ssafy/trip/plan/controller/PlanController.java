package com.ssafy.trip.plan.controller;

import com.ssafy.trip.common.Message;
import com.ssafy.trip.plan.model.PlanDto;
import com.ssafy.trip.plan.model.service.PlanService;
import com.ssafy.trip.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/plan")
public class PlanController {
    private final PlanService planService;

    @GetMapping(value = "/planList")
    public ResponseEntity<?> getPlanList(@RequestParam Map<String, Object> map) {
        return ResponseEntity.ok().body(Message.success(planService.getPlanList(map)));
    }

    @PostMapping("/planRegist")
    @PreAuthorize("isAuthenticated() and #requestMap['plan']['planUserId'] == authentication.principal.userId")
    public ResponseEntity<?> setPlan(@RequestBody Map<String, Object> requestMap){
        planService.setPlan(requestMap);
        return ResponseEntity.ok().body(Message.success());
    }

    @GetMapping("/{planNo}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getPlan(@PathVariable("planNo") int planNo) {
        return ResponseEntity.ok().body(Message.success(planService.getPlanAndItem(planNo)));
    }

    @DeleteMapping("/{planNo}")
    @PreAuthorize("(isAuthenticated() and #map['planUserId'] == authentication.principal.userId) or hasRole('ADMIN')")
    public ResponseEntity<?> deletePlan(@PathVariable("planNo") int planNo, @RequestBody Map<String, String> map) {
        planService.deletePlanAndItem(planNo);
        return ResponseEntity.ok().body(Message.success());
    }
}
