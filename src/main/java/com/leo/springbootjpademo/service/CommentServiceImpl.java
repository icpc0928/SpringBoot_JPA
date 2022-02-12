package com.leo.springbootjpademo.service;

import com.leo.springbootjpademo.domain.Comment;
import com.leo.springbootjpademo.domain.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    CommentRepository commentRepository;


    @Transactional
    @Override
    public Comment saveComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Transactional
    @Override
    public void deleteComment(Long id) {
        //我要刪除的comment的對象跟article的List<Comment>是有關聯的 故不讓刪除
//        commentRepository.deleteById(id);
        //刪除方法1. 直接用JPQL語句直接刪除 因這兩張表的關係其實是允許我直接刪除Comment的資料 而不會影響到Article
        //缺點是 如果目前記憶體正在使用相對的Article 他的List<Comment>中還是存在一個被我們刪除的資料 會是個隱患
//        commentRepository.deleteBy(id);

        //刪除方法2. 先解除關係 再刪除
//        Comment comment = commentRepository.findById(id).get();
//        List<Comment> comments = comment.getArticle().getComments();//找出這個評論的對應文章
//        //從文章的集合評論中 去除此id的Comment
//        comments.removeIf(comment1 -> id.equals(comment1.getId()));
//        //才能做刪除
//        commentRepository.deleteById(id);

        //刪除方法3. 依據方法2 直接把刪除方法更簡單的寫在Comment物件內
        Comment comment = commentRepository.findById(id).get();
        comment.clearComment();
        commentRepository.deleteById(id);
    }
}
