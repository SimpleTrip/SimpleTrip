package com.ssafy.trip.place.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssafy.trip.place.model.Place;
import com.ssafy.trip.place.model.mapper.PlaceMapper;


@Service
public class PlaceServiceImpl implements PlaceService {
	
	private PlaceMapper placeMapper;
	
	public PlaceServiceImpl(PlaceMapper placeMapper) {
		this.placeMapper = placeMapper;
	}

	@Override
	public void registPlace(Place place) {
		placeMapper.registPlace(place);
	}

	@Override
	public Place viewPlace(int placeId) {
		return placeMapper.viewPlace(placeId);
	}

	@Override
	public List<Place> getPlaceList(Map<String, Object> listInfo) {
		return placeMapper.getPlaceList(listInfo);
	}
	
	@Override
	public int getPlaceListSize(Map<String, Object> listInfo) {
		return placeMapper.getPlaceListSize(listInfo);
	}


}
