package com.ssafy.trip.favorite.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.trip.favorite.model.Favorite;
import com.ssafy.trip.favorite.model.service.FavoriteService;

@RestController
@RequestMapping("/favorites")
public class FavoriteController {
	
	private FavoriteService favoriteService;

	public FavoriteController(FavoriteService favoriteService) {
		this.favoriteService = favoriteService;
	}
	
	@PostMapping
	ResponseEntity<?> addFavorite(@RequestBody Favorite favorite) {
		try {
			favoriteService.addFavorite(favorite);
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (Exception e) {
			return handleException(e);
		}
	}

	@GetMapping("/{user-id}")
	ResponseEntity<?> getFavoriteList(
			@PathVariable("user-id") String userId) {
		try {
			List<Favorite> favoriteList = favoriteService.getFavoriteList(userId);
			System.out.println(favoriteList);
			if (favoriteList != null && favoriteList.size() > 0)
				return new ResponseEntity<List<Favorite>>(favoriteList, HttpStatus.OK);
			else return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return handleException(e);
		}
	}
	
	@DeleteMapping
	ResponseEntity<?> deleteFavorite(@RequestBody Favorite favorite) {
		try {
			System.out.println(favorite.toString());
			String userId = favorite.getUserId();
			if (userId != null && userId != "") {				
				favoriteService.deleteFavorite(favorite);
				List<Favorite> favoriteList = favoriteService.getFavoriteList(userId);
				if (favoriteList != null)
					return new ResponseEntity<List<Favorite>>(favoriteList, HttpStatus.OK);
				else return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}
			else {
				return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return handleException(e);
		}
	}
	
	ResponseEntity<String> handleException(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
