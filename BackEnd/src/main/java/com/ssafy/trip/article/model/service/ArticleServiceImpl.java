package com.ssafy.trip.article.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssafy.trip.article.exception.ArticleErrorCode;
import com.ssafy.trip.article.exception.ArticleException;
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
		Article a = articleMapper.view(articleId);
		if (a == null) throw new ArticleException(ArticleErrorCode.NON_EXIST);
		return articleMapper.view(articleId);
	}

	@Override
	public void modify(Article article) {
		Article a = articleMapper.view(article.getArticleId());
		System.out.println(a);
		if (a == null) throw new ArticleException(ArticleErrorCode.NON_EXIST);
		articleMapper.modify(article);
	}
	
	@Override
	public void delete(int articleId) {
		view(articleId);
		articleMapper.delete(articleId);
	}

	@Override
	public int getListSize(Map<String, Object> map) throws Exception {
		return articleMapper.getListSize(map);
	}
}
