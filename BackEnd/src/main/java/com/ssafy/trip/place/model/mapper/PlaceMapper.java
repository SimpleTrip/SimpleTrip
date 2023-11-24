package com.ssafy.trip.place.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.trip.place.model.Place;

@Mapper
public interface PlaceMapper {
	void registPlace(Place place);
	Place viewPlace(int placeId);
	List<Place> getPlaceList(Map<String, Object> listInfo);
	int getPlaceListSize(Map<String, Object> listInfo);
}
