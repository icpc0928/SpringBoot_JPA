package com.leo.springbootjpademo;

import com.alibaba.fastjson.JSON;
import com.leo.springbootjpademo.domain.Article;
import com.leo.springbootjpademo.domain.Comment;
import com.leo.springbootjpademo.service.ArticleService;
import com.leo.springbootjpademo.service.CommentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class CommentTest {

    @Autowired
    private CommentService commentService;

    @Autowired
    private ArticleService articleService;

    //對某篇文章新增一筆評論
    @Test
    public void saveCommentTest(){
        Article article = articleService.findArticle(3L);

        Comment comment = new Comment();
        comment.setContent("關於這個評論我覺得...");
        comment.setArticle(article);
        commentService.saveComment(comment);
    }

    //刪除評論
    @Test
    public void deleteCommentTest(){
        commentService.deleteComment(3L);
    }

}
