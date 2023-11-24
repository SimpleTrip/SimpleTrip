package com.ssafy.trip.spot.model.service;

import org.springframework.stereotype.Service;

import com.ssafy.trip.spot.model.Spot;
import com.ssafy.trip.spot.model.mapper.SpotMapper;

@Service
public class SpotServiceImpl implements SpotService {
	
	private SpotMapper spotMapper;

	public SpotServiceImpl(SpotMapper spotMapper) {
		this.spotMapper = spotMapper;
	}

	@Override
	public void addSpot(Spot spot) {
		spotMapper.addSpot(spot);
	}
}
