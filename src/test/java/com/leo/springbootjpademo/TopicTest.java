package com.leo.springbootjpademo;

import com.alibaba.fastjson.JSON;
import com.leo.springbootjpademo.domain.Article;
import com.leo.springbootjpademo.domain.Comment;
import com.leo.springbootjpademo.domain.Topic;
import com.leo.springbootjpademo.service.ArticleService;
import com.leo.springbootjpademo.service.TopicService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class TopicTest {

    @Autowired
    private TopicService topicService;

    @Test
    public void saveTopic(){
        Topic topic = new Topic();
        topic.setName("專題名稱1");
        topicService.saveTopic(topic);
    }

    @Test
    public void updateTopic(){
        Topic topic = topicService.findTopic(1L);
        topic.setName("文學創作類");
        topicService.updateTopic(topic);
    }

    //設置關係 於關聯表中新增
    @Test
    public void includeArticle(){
        topicService.includeArticle(1L, 2L);
    }

    //failed to lazily initialize a collection of role:- no Session 一樣多對多 兩方默認都是LAZY
    @Test
    public void findTopic(){
        Topic topic = topicService.findTopic(1L);
        //打印的工作寫在TopicServiceImpl內 在事務處理中(Session關閉前)取得Articles並打印
//        topic.getArticles();
//        System.out.println(JSON.toJSONString(topic, true));
    }

    //取消對應關係
    @Test
    public void unIncludeArticle(){
        topicService.unIncludeArticle(1L, 2L);
    }

    //刪除Topic會自動刪除對應關係內的資料嗎?? 會的~ 因為Topic是關係維護方 ,故刪除本身時會連同關係資料表一同刪除
    //如果是被維護方刪除,並希望關係表 也一同刪除,則需要在被維護方中移除物件才能刪除(如多對一那裏的作法)
    @Test
    public void deleteTopic(){
        topicService.deleteTopic(1L);
    }

}
