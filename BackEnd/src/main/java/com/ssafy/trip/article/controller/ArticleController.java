package com.ssafy.trip.article.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.trip.article.model.Article;
import com.ssafy.trip.article.model.service.ArticleService;
import com.ssafy.trip.common.Message;
import com.ssafy.trip.util.PageUtil;

@RestController
@RequestMapping("/articles")
public class ArticleController {

	private ArticleService articleService;

	public ArticleController(ArticleService articleService) {
		this.articleService = articleService;
	}

	@PostMapping
	@PreAuthorize("isAuthenticated() and #article.userId == authentication.principal.userId")
	ResponseEntity<?> writeArticle(@RequestBody Article article) {
		articleService.write(article);
		return ResponseEntity.ok().body(Message.success());
	}

	@GetMapping
	ResponseEntity<?> listArticles(@RequestParam Map<String, Object> map) {
		try {
			Map<String, Object> result = new HashMap<>();
			Map<String, Object> params = PageUtil.getStartAndListSize(map);
			params.put("word", map.get("word"));
			params.put("key", map.get("key"));
			int totalQnaCount = articleService.getListSize(params);
			int totalPageCount = (totalQnaCount - 1) / 10 + 1;
			List<Article> articles = articleService.list(params);

			result.put("totalPgno", totalPageCount);
			result.put("articleList", articles);

			if (articles != null)
				return new ResponseEntity<>(result, HttpStatus.OK);
			else return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return handleException(e);
		}
	}

	@GetMapping("/{articleId}")
	@PreAuthorize("isAuthenticated()")
	ResponseEntity<?> viewArticle(@PathVariable("articleId") int articleId) {
		return ResponseEntity.ok().body(Message.success(articleService.view(articleId)));
	}

	@PutMapping("/{articleId}")
	@PreAuthorize("isAuthenticated() and #article.userId == authentication.principal.userId")
	ResponseEntity<?> modifyArticle(@PathVariable("articleId") int articleId, @RequestBody Article article) {
		articleService.modify(article);
		return ResponseEntity.ok().body(Message.success());
	}

	@DeleteMapping("/{articleId}")
	@PreAuthorize("isAuthenticated() and #requestMap['articleUserId'] == authentication.principal.userId")
	ResponseEntity<?> deleteArticle(@PathVariable("articleId") int articleId, @RequestBody Map<String, String> requestMap) {
		articleService.delete(articleId);
		return ResponseEntity.ok().body(Message.success());
	}

	ResponseEntity<String> handleException(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
