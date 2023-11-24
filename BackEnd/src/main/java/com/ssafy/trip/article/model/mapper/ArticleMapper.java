package com.ssafy.trip.article.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.trip.article.model.Article;

@Mapper
public interface ArticleMapper {
	void write(Article article);
	List<Article> list(Map<String, Object> params);
	Article view(int articleId);
	void modify(Article article);
	void delete(int articleId);
	int getListSize(Map<String, Object> map) throws Exception;
}
