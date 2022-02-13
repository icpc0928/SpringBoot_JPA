package com.leo.springbootjpademo.service;

import com.alibaba.fastjson.JSON;
import com.leo.springbootjpademo.domain.Article;
import com.leo.springbootjpademo.domain.ArticleRepository;
import com.leo.springbootjpademo.domain.Topic;
import com.leo.springbootjpademo.domain.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TopicServiceImpl implements TopicService{

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Transactional
    @Override
    public Topic saveTopic(Topic topic) {
        return topicRepository.save(topic);
    }

    @Transactional
    @Override
    public Topic updateTopic(Topic topic) {
        return topicRepository.save(topic);
    }

    //在一個事務裡面就是一個session 在session關閉之前去取得 避免LAZY的關係而找不到
    @Transactional
    @Override
    public Topic findTopic(Long id) {
        Topic topic = topicRepository.findById(id).orElse(null);
        System.out.println(JSON.toJSONString(topic, true));
        return topic;

    }

    @Transactional
    @Override
    public Topic includeArticle(Long topicId, Long articleId) {
        Topic topic = topicRepository.findById(topicId).orElse(new Topic());
        Article article = articleRepository.findById(articleId).orElse(null);
        topic.getArticles().add(article);
    //有設置Transactional事務時 在session提交後就會進行自動保存,並將資料庫同步更新,故設置事務後就不用再寫save了
//        return topicRepository.save(topic);
        return topic;
    }

    @Transactional
    @Override
    public Topic unIncludeArticle(Long topicId, Long articleId) {
        Topic topic = topicRepository.findById(topicId).orElse(new Topic());
        Article article = articleRepository.findById(articleId).orElse(null);
        topic.getArticles().remove(article);

        //有設置Transactional事務時 在session提交後就會進行自動保存,並將資料庫同步更新,故設置事務後就不用再寫save了
        //        return topicRepository.save(topic);

        //因為有設置事務處理 當對應的物件做某些更動並且提交時(return即提交並結束事務) 事務處理結束前會把對應的結果更新到資料庫中,故這裡移除了一筆article後 資料庫對更新對應的結果(即刪除對應表中的對應資料)
        return topic;
    }

    @Transactional
    @Override
    public void deleteTopic(Long id) {
        topicRepository.deleteById(id);
    }
}
