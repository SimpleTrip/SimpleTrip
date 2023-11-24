package com.ssafy.trip.spot.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.trip.spot.model.Spot;
import com.ssafy.trip.spot.model.mapper.SpotMapper;
import com.ssafy.trip.user.model.User;
import com.ssafy.trip.user.model.typehandlers.Sex;

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

	@Override
	public List<Spot> getPopularSpotList(Sex userSex, int userAge) {
		return spotMapper.getPopularSpotList(userSex, userAge);
	}
}
