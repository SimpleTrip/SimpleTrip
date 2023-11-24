package com.ssafy.trip.article.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssafy.trip.article.model.Article;
import com.ssafy.trip.article.model.mapper.ArticleMapper;

@Service
public class ArticleServiceImpl implements ArticleService {
	
	private ArticleMapper articleMapper;

	public ArticleServiceImpl(ArticleMapper articleMapper) {
		this.articleMapper = articleMapper;
	}

	@Override
	public void write(Article article) {
		articleMapper.write(article);
	}
	
	@Override
	public List<Article> list(Map<String, Object> params) {
		return articleMapper.list(params);
	}

	@Override
	public Article view(int articleId) {
		return articleMapper.view(articleId);
	}

	@Override
	public void modify(Article article) {
		articleMapper.modify(article);
	}
	
	@Override
	public void delete(int articleId) {
		articleMapper.delete(articleId);
	}

	@Override
	public int getListSize() throws Exception {
		return articleMapper.getListSize();
	}
}
