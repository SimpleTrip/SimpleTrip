package com.ssafy.trip.article.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.trip.article.model.Article;

public interface ArticleService {
	void write(Article article);
	List<Article> list(Map<String, Object> params);
	Article view(int articleId);
	void modify(Article article);
	void delete(int articleId);
	int getListSize() throws Exception;
}
