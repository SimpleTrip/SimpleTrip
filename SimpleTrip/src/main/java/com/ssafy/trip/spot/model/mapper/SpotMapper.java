package com.ssafy.trip.spot.model.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.trip.spot.model.Spot;

@Mapper
public interface SpotMapper {
	void addSpot(Spot spot);
}
