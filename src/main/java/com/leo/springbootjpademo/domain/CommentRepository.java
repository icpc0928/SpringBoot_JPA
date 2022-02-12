package com.leo.springbootjpademo.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Transactional
    @Modifying  //更新或刪除時需要加的
    @Query(value = "delete from Comment c where c.id = ?1")
    void deleteBy(Long id);
}
