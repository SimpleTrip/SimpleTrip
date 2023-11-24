package com.ssafy.trip.article.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ssafy.trip.qna.model.QnaDto;
import com.ssafy.trip.util.PageUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ssafy.trip.article.model.Article;
import com.ssafy.trip.article.model.service.ArticleService;

@RestController
@RequestMapping("/articles")
public class ArticleController {

	private ArticleService articleService;

	public ArticleController(ArticleService articleService) {
		this.articleService = articleService;
	}

	@PostMapping
	ResponseEntity<?> writeArticle(@RequestBody Article article) {
		try {			
			articleService.write(article);
			Article a = articleService.view(article.getArticleId()); 
			if (a != null)
				return new ResponseEntity<Article>(article, HttpStatus.OK);
			else
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return handleException(e);
		}
	}

	@GetMapping
	ResponseEntity<?> listArticles(@RequestParam Map<String, String> map) {
		try {
			Map<String, Object> result = new HashMap<>();
			Map<String, Object> params = PageUtil.getStartAndListSize(map);
			int totalQnaCount = articleService.getListSize();
			int totalPageCount = (totalQnaCount - 1) / 10 + 1;
			List<Article> articles = articleService.list(params);

			result.put("totalPgno", totalPageCount);
			result.put("articleList", articles);

			if (articles != null)
				return new ResponseEntity<>(result, HttpStatus.OK);
			else return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return handleException(e);
		}
	}

	@GetMapping("/{article-id}")
	ResponseEntity<?> viewArticle(@PathVariable("article-id") int articleId) {
		try {
			Article article = articleService.view(articleId);
			if (article != null)
				return new ResponseEntity<Article>(article, HttpStatus.OK);
			else return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return handleException(e);
		}
	}

	@PutMapping("/{article-id}")
	ResponseEntity<?> modifyArticle(@PathVariable("article-id") int articleId, @RequestBody Article article) {
		try {
			articleService.modify(article);
			return new ResponseEntity<Article>(article, HttpStatus.CREATED);
		} catch (Exception e) {
			return handleException(e);
		}
	}

	@DeleteMapping("/{article-id}")
	ResponseEntity<?> deleteArticle(@PathVariable("article-id") int articleId) {
		try {
			articleService.delete(articleId);
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (Exception e) {
			return handleException(e);
		}
	}

	ResponseEntity<String> handleException(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
