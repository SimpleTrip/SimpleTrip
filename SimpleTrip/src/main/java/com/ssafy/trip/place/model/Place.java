package com.ssafy.trip.place.model;

import java.sql.Date;

import lombok.Data;

@Data
public class Place {
	int placeId;
	String placeName;
	String placeAddr;
	String placeType;
	String placeContent;
	Date placeDate;
	String placeImgUrl;
	double placeLat;
	double placeLng;
}
