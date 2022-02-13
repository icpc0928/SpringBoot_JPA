package com.leo.springbootjpademo.service;

import com.leo.springbootjpademo.domain.Topic;

public interface TopicService {

    Topic saveTopic(Topic topic);

    Topic updateTopic(Topic topic);

    Topic findTopic(Long id);

    Topic includeArticle(Long topicId, Long articleId);

    Topic unIncludeArticle(Long topicId, Long articleId);

    void deleteTopic(Long id);

}
