package com.leo.springbootjpademo.web;

import com.leo.springbootjpademo.domain.Author;
import com.leo.springbootjpademo.domain.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authors")
public class TestController {

    @Autowired
    private AuthorRepository authorRepository;

    @GetMapping
    public Object findAuthorForPage(@PageableDefault(page = 0, size = 5, sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable){

        return authorRepository.findAll(pageable);
    }

}
