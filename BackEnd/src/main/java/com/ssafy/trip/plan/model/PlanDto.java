package com.ssafy.trip.plan.model;

import lombok.Data;

@Data
public class PlanDto {
    private int planNo;
    private String planTitle;
    private String planContent;
    private String planUserId;
    private String registDate;
    private String startDate;
    private String endDate;
}
