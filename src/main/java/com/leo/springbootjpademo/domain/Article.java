package com.leo.springbootjpademo.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;

    //一對多的關係 通常而言 維護方為多的那面,故這裡是"被維護方"
    //聯級作 新增 刪除 (更新沒必要,文章更新評論不用可以不用改動)
    //fetch 一對多時, 一的這端 默認FetchType是LAZY 因為我拿到文章可能有多個評論,當數據量大的話可以節省效能 相反 多的那端 默認會是EAGER
    @OneToMany(mappedBy = "article", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    private List<Comment> comments = new ArrayList<>();

    //讓Topic作為維護方 這裡標註mappedBy指向過去
    //聯級應用在這個功能當中目前用不到 故不設置
    @ManyToMany(mappedBy = "articles")
    private List<Topic> topics = new ArrayList<>();

    public Article() {
    }

    //設置評論的簡便方法
    public void addComment(Comment comment){
        comment.setArticle(this);
        comments.add(comment);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Topic> getTopics() {
        return topics;
    }

    public void setTopics(List<Topic> topics) {
        this.topics = topics;
    }
}
