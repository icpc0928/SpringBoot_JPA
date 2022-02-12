package com.leo.springbootjpademo.domain;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal balance;

    //因為Wallet是OneToOne的被維護者 所以添加mappedBy 註明是Author內的wallet參數名稱(可以Ctrl點下面的名稱會指向Author內的Wallet)
    //即建立雙向的一對一關聯 好處是當只有Wallet的ID時 也能找到對應的Author
    //這裡也可以做聯級的應用
    @OneToOne(mappedBy = "wallet")
    private Author author;

    public Wallet() {
    }

    public Wallet(BigDecimal balance) {
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}
