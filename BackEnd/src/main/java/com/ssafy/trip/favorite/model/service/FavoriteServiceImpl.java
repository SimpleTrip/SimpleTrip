package com.ssafy.trip.favorite.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.trip.favorite.model.Favorite;
import com.ssafy.trip.favorite.model.mapper.FavoriteMapper;

@Service
public class FavoriteServiceImpl implements FavoriteService {
	
	private FavoriteMapper favoriteMapper;
	
	public FavoriteServiceImpl(FavoriteMapper favoriteMapper) {
		this.favoriteMapper = favoriteMapper;
	}

	@Override
	public void addFavorite(Favorite favorite) {
		favoriteMapper.addFavorite(favorite);
	}

	@Override
	public List<Favorite> getFavoriteList(String userId) {
		return favoriteMapper.getFavoriteList(userId);
	}

	@Override
	public void deleteFavorite(Favorite favorite) {
		favoriteMapper.deleteFavorite(favorite);
	}
	
	
}
