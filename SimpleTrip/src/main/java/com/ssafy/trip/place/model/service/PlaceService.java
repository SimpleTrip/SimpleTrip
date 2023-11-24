package com.ssafy.trip.place.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.trip.place.model.Place;

public interface PlaceService {
	void registPlace(Place place);
	Place viewPlace(int placeId);
	List<Place> getPlaceList(Map<String, Object> listInfo);
	int getPlaceListSize(Map<String, Object> listInfo);
}
