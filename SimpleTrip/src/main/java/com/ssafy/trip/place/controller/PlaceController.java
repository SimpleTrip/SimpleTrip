package com.ssafy.trip.place.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.ssafy.trip.article.model.Article;
import com.ssafy.trip.common.Message;
import com.ssafy.trip.place.model.Place;
import com.ssafy.trip.place.model.service.PlaceService;
import com.ssafy.trip.util.PageUtil;

@RestController
@RequestMapping("/places")
public class PlaceController {

	private PlaceService placeService;

	public PlaceController(PlaceService placeService) {
		this.placeService = placeService;
	}
	
	@PostMapping
    @PreAuthorize("isAuthenticated()")
	ResponseEntity<?> registPlace(@RequestBody Place place) {
		placeService.registPlace(place);
        return ResponseEntity.ok().body(Message.success());
	}
	
	@GetMapping("/{place-id}")
    @PreAuthorize("isAuthenticated()")
	ResponseEntity<?> viewPlace(@PathVariable("place-id") int placeId) {
        return ResponseEntity.ok().body(Message.success(placeService.viewPlace(placeId)));
	}
	
	@GetMapping
	ResponseEntity<?> getPlaceList(@RequestParam Map<String, Object> listInfoMap) {
		try {
			Map<String, Object> response = new HashMap<>();

			Map<String, Object> listInfo = PageUtil.getStartAndListSize(listInfoMap);
			listInfo.put("key", listInfoMap.get("key"));
			listInfo.put("word", listInfoMap.get("word"));
			
			List<Place> placeList = placeService.getPlaceList(listInfo);
			response.put("placeList", placeList);
			
			int placeListSize = placeService.getPlaceListSize(listInfo);
			response.put("placeListSize", placeListSize);
			
			int placeListPageCount = (placeListSize - 1) / 10 + 1;
			response.put("placeListPageCount", placeListPageCount);
			
			if (placeList != null)
				return new ResponseEntity<>(response, HttpStatus.OK);
			else return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return handleException(e);
		}
	}
	
	ResponseEntity<String> handleException(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
