package com.leo.springbootjpademo.service;

import com.leo.springbootjpademo.domain.Article;
import com.leo.springbootjpademo.domain.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ArticleServiceImpl implements ArticleService{

    @Autowired
    private ArticleRepository articleRepository;

    @Transactional
    @Override
    public Article saveArticle(Article article) {
        return articleRepository.save(article);
    }

    @Transactional
    @Override
    public Article updateArticle(Article article) {
        return articleRepository.save(article);
    }

    @Override
    public Article findArticle(Long id) {
        return articleRepository.findById(id).get();
    }

    @Transactional
    @Override
    public void deleteArticle(Long id) {
        articleRepository.deleteById(id);
    }
}
