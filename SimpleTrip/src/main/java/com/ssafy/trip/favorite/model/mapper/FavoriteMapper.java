package com.ssafy.trip.favorite.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.trip.favorite.model.Favorite;

@Mapper
public interface FavoriteMapper {
	void addFavorite(Favorite favorite);
	List<Favorite> getFavoriteList(String userId);
	void deleteFavorite(Favorite favorite);
}
