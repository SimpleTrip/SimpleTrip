package com.ssafy.trip.spot.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.trip.common.Message;
import com.ssafy.trip.spot.model.Spot;
import com.ssafy.trip.spot.model.service.SpotService;
import com.ssafy.trip.user.model.typehandlers.Sex;

@RestController
@RequestMapping("/spots")
public class SpotController {

	private SpotService spotService;

	public SpotController(SpotService spotService) {
		this.spotService = spotService;
	}
	
	@PostMapping("/{userId}")
    @PreAuthorize("isAuthenticated() and #userId == authentication.principal.userId")
	ResponseEntity<?> addSpot(@PathVariable("userId") String userId, @RequestBody Spot spot) {
		spotService.addSpot(spot);
        return ResponseEntity.ok().body(Message.success());
	}
	
	@GetMapping("/{userId}")
    @PreAuthorize("isAuthenticated() and #userId == authentication.principal.userId")
	ResponseEntity<?> getPopularSpotList(@PathVariable("userId") String userId, @RequestParam("userSex") Sex userSex, @RequestParam("userAge") int userAge) {
        return ResponseEntity.ok().body(Message.success(spotService.getPopularSpotList(userSex, userAge)));
	}
	
	ResponseEntity<String> handleException(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
