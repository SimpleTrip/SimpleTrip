package com.ssafy.trip.favorite.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.trip.common.Message;
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
    @PreAuthorize("isAuthenticated() and #favorite.userId == authentication.principal.userId")
	ResponseEntity<?> addFavorite(@RequestBody Favorite favorite) {
		System.out.println(favorite.getUserId());
		favoriteService.addFavorite(favorite);		
        return ResponseEntity.ok().body(Message.success());
	}

	@GetMapping("/{userId}")
    @PreAuthorize("isAuthenticated()")
	ResponseEntity<?> getFavoriteList(@PathVariable("userId") String userId) {
        return ResponseEntity.ok().body(Message.success(favoriteService.getFavoriteList(userId)));
	}
	
	@DeleteMapping
    @PreAuthorize("isAuthenticated() and #favorite.userId == authentication.principal.userId")
	ResponseEntity<?> deleteFavorite(@RequestBody Favorite favorite) {
		favoriteService.deleteFavorite(favorite);		
        return ResponseEntity.ok().body(Message.success());
	}
}
