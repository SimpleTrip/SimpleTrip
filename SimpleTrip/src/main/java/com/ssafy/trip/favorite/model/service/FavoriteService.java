package com.ssafy.trip.favorite.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.trip.favorite.model.Favorite;

public interface FavoriteService {
	void addFavorite(Favorite favorite);
	List<Favorite> getFavoriteList(String userId);
	void deleteFavorite(Favorite favorite);
}
