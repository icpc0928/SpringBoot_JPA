package com.leo.springbootjpademo;

import com.alibaba.fastjson.JSON;
import com.leo.springbootjpademo.domain.Author;
import com.leo.springbootjpademo.domain.AuthorRepository;
import com.leo.springbootjpademo.domain.Wallet;
import com.leo.springbootjpademo.domain.WalletRepository;
import com.leo.springbootjpademo.service.AuthorService;
import com.leo.springbootjpademo.service.AuthorServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.Date;

@SpringBootTest
public class AuthorTest {

    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private AuthorService authorService;
    @Autowired
    private WalletRepository walletRepository;

    //unitTest 對saveAuthorTest按右鍵 run....就能跑這個單元測試
    @Test
    public void saveAuthorTest(){
        Author author = new Author();
        author.setNickName("Leoo");
        author.setPhone("0934343434");
        author.setSignDate(new Date());
        author.setWallet(new Wallet(new BigDecimal(188.5)));

        authorRepository.save(author);
    }

    @Test
    public void updateAuthor(){
        Author author = authorService.findAuthor(11L);
        author.setPhone("0912345679");
        Wallet wallet = author.getWallet();
        wallet.setBalance(new BigDecimal(288.88));  //聯級更新才會更新到wallet的資料表
        author.setWallet(wallet);
        authorService.updateAuthor(author);
    }

    @Test
    public void findAuthorTest(){
//        List<Author> authors = authorRepository.findByPhoneAndNickName("0934175258", "Leo");
//        List<Author> authors = authorRepository.findDistinctByNickNameIgnoreCaseAndPhoneOrderBySignDateDesc("LEO", "0934175258");

//        List<Author> authors = authorRepository.findByNickNameLike("%L%");
//        List<Author> authors = authorRepository.findByPhoneLike("8");
//        List<Object[]> authors = authorRepository.findArray("L");
//        List<Author> authors = authorRepository.findByNickName("L", Sort.by(Sort.Direction.ASC, "signDate"));
//        List<Author> authors = authorRepository.findByPhoneLike2("8" , "6");
//        List<Author> authors = authorRepository.findBySQL("L");
//        int i = authorRepository.setNickName("Leeee", "0916215180");
//        System.out.println(JSON.toJSONString(authors, true));

        Author author = authorService.findAuthor(11L);
        System.out.println(JSON.toJSONString(author, true));
    }

    @Test
    public void deleteAuthorTest(){
        authorService.deleteAuthor(11L);
    }

    @Test
    public void findAuthorForPageTest(){
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        Pageable pageable = PageRequest.of(0, 5, sort);
        Page<Author> page = authorRepository.findAll(pageable);

        System.out.println(JSON.toJSONString(page, true));
    }

    @Test
    public void transactionalTest(){
        authorService.updateAuthor();
    }

    @Test
    public void findWalletTest(){
        Wallet wallet = walletRepository.findById(1L).get();
        System.out.println(JSON.toJSONString(wallet, true));

    }

}
