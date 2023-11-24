package com.ssafy.trip.article.model;

import lombok.Data;

@Data
public class Article {
	int articleId;
	String articleTitle;
	String articleContent;
	String userId;
}
