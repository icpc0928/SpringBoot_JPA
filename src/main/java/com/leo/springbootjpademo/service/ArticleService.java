package com.leo.springbootjpademo.service;

import com.leo.springbootjpademo.domain.Article;

public interface ArticleService {

    Article saveArticle(Article article);

    Article updateArticle(Article article);

    Article findArticle(Long id);

    void deleteArticle(Long id);
}
