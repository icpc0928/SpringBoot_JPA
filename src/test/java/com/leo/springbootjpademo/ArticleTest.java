package com.leo.springbootjpademo;

import com.alibaba.fastjson.JSON;
import com.leo.springbootjpademo.domain.*;
import com.leo.springbootjpademo.service.ArticleService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import java.util.List;


@SpringBootTest
public class ArticleTest {

    @Autowired
    private ArticleService articleService;

    @Test
    public void saveArticle(){
        Article article = new Article();
        article.setTitle("關於創業");
        article.setContent("關於創業的一些想法...");

        Comment comment1 = new Comment("評論1");
        Comment comment2 = new Comment("評論2");

        article.addComment(comment1);
        article.addComment(comment2);

        //因為最終只存Article 需要將Comment setArticle 才會在Comment存入Article的對應id
        articleService.saveArticle(article);
    }

    @Test
    public void updateArticle(){
        Article article = articleService.findArticle(3L);
        article.setContent("一篇遊記的內容");
        articleService.updateArticle(article);
    }

    @Test
    public void findArticle(){
        Article article = articleService.findArticle(3L);
        //failed to lazily initialize a collection of role: 延遲加載報錯 因為拿到Comment的List (OneToMany的默認Fetch是LAZY)
        System.out.println(JSON.toJSONString(article, true));
    }

    @Test
    public void deleteArticle(){
        //已經設置聯級刪除 會自動刪除Comment資料表內的相關內容
        articleService.deleteArticle(1L);
    }

}
