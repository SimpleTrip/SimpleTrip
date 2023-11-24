package com.ssafy.trip.spot.model.service;

import java.util.List;

import com.ssafy.trip.spot.model.Spot;
import com.ssafy.trip.user.model.User;
import com.ssafy.trip.user.model.typehandlers.Sex;

public interface SpotService {
	void addSpot(Spot spot);
	List<Spot> getPopularSpotList(Sex userSex, int userAge);
}
