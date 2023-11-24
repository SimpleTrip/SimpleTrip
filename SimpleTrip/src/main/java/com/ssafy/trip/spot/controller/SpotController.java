package com.ssafy.trip.spot.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.trip.spot.model.Spot;
import com.ssafy.trip.spot.model.service.SpotService;

@RestController
@RequestMapping("/spots")
public class SpotController {

	private SpotService spotService;

	public SpotController(SpotService spotService) {
		this.spotService = spotService;
	}
	
	@PostMapping
	ResponseEntity<?> addSpot(@RequestBody Spot spot) {
		try {
			spotService.addSpot(spot);
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (Exception e) {
			return handleException(e);
		}
	}
	
	ResponseEntity<String> handleException(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
