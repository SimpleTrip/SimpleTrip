package com.ssafy.trip.favorite.model;

import lombok.Data;

@Data
public class Favorite {
	int favoriteId;
	String userId;
	int spotId;
	//
	String spotName;
	String spotAddr;
	String spotImgUrl;
}
