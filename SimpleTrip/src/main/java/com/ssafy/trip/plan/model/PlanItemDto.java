package com.ssafy.trip.plan.model;

import lombok.Data;

@Data
public class PlanItemDto {
    private int id;
    private String planNo;
    private String placeName;
    private String addressName;
    private String roadAddressName;
    private String categoryGroupCode;
    private String categoryGroupName;
    private String categoryName;
    private String placeUrl;
    private String x;
    private String y;
    private String memo;
}
