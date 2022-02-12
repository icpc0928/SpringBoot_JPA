package com.leo.springbootjpademo.domain;

import javax.persistence.*;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;

    //多對一的關係 通常由多的那方做維護方 故Comment為維護方 由Article做mappedBy
    //多對一 多的這端 fetch默認是 EAGER 當我拿到一個評論 肯定只有一個文章 比較不會影響效能 相反 一的那端 拿到文章可能會有多個評論 默認就會是LAZY (節省效能)
    @ManyToOne  //評論的新增刪除修改更新等等 不太應該要去動到文章,故這裡就不做聯級應用
    private Article article;

    //清除Article中的List<Comment>的對應關係
    public void clearComment(){
        this.getArticle().getComments().remove(this);
    }



    public Comment() {
    }

    public Comment(String content) {
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }
}
