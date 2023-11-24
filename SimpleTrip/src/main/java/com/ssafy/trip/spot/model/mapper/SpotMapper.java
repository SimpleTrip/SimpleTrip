package com.ssafy.trip.spot.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ssafy.trip.spot.model.Spot;
import com.ssafy.trip.user.model.typehandlers.Sex;

@Mapper
public interface SpotMapper {
	void addSpot(Spot spot);
	List<Spot> getPopularSpotList(@Param("userSex") Sex userSex, @Param("userAge") int userAge);
}
