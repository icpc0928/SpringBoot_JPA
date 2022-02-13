package com.leo.springbootjpademo.service;

import com.leo.springbootjpademo.domain.Author;
import com.leo.springbootjpademo.domain.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService{

    @Autowired
    private AuthorRepository authorRepository;

    @Transactional  //納入事務管理 有兩種操作以上就須做 或者查詢/刪除時,可做到當第一條成功 第二條失敗時 會回復到第一條之前
    @Override
    public Author updateAuthor() {

        Author author = new Author();
        author.setPhone("0912345678");
        author.setNickName("Test123");
        author.setSignDate(new Date());
        Author author1 = authorRepository.save(author); //這個物件就包含id了
        author1.setPhone("0987651");   //若長度超出允許範圍,故意出錯--->因為注入事務管理,上一行的save就不會存進資料庫
        Author author2 = authorRepository.save(author1); //然後用有id的去做save 就不是更新,而是保存

        int i = 8/0; //<-一定會拋出異常,看前面兩個一樣沒有存入資料庫, 故事務管理 內部只要出現異常,全部的操作都不會進行

        return author2;
    }
    @Transactional
    @Override
    public Author saveAuthor(Author author) {
        return authorRepository.save(author);
    }

    @Transactional
    @Override
    public Author updateAuthor(Author author) {
        return authorRepository.save(author);
    }

    @Transactional
    @Override
    public Author findAuthor(Long id) {
        return authorRepository.findById(id).orElse(new Author());
    }

    @Transactional
    @Override
    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }
}
