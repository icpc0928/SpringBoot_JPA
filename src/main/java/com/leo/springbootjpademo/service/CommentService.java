package com.leo.springbootjpademo.service;

import com.leo.springbootjpademo.domain.Comment;

public interface CommentService {

    Comment saveComment(Comment comment);

    void deleteComment(Long id);
}
