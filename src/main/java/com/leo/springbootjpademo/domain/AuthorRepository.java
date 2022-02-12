package com.leo.springbootjpademo.domain;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Repository 接口
 * Spring Data JPA 簡化了持久層的操作，開發者只需要聲明持久層接口，而不需要實現該街口。Spring Data JPA內部會根據不同的接口方法，採用不同的策略自動生成實現。
 * 開發者聲明持久層接口，需要直接或間接的方式"繼承"Repository接口，從而使自定義的持久層擁有了持久層的操作能力。
 *
 * Repository接口:
 *  *Repository* 是Spring Data的核心接口，最頂層的接口，不包括任何方法，它的目的是為了統一所有的Repository的類型，且讓組件掃描的時候自動識別。
 * Repository擴展接口:
 *  *CrudRepository* 繼承了Repository接口，提供了增刪改查方法，可以直接調用。
 *  *PagingAndSortingRepository* 繼承了CrudRepository，又提供了分頁和排序兩個方法。
 *  *JpaRepository* 更高層的接口，繼承了PagingAndSortingRepository，針對於JPA技術的接口，提供了flush(),saveFlush(),deleteInBatch()...等等方法
 *
 *
 *  使用步驟:
 *  1.新建一個接口(interface) 繼承持久層接口(JpaRepository)
 *  2.在持久層接口中聲明業務方法，除了原本接口的預設方法外，也可自訂自己要的方法
 *  3.獲取Repository實例(以AuthorTests為例子(在test資料夾內))並且直接使用: 即在該物件中注入@Autowired AuthorRepository,即可在該物件中使用這個接口的方法
 *
 */

public interface AuthorRepository extends JpaRepository<Author, Long> {

    List<Author> findByPhoneAndNickName(String phone, String nickName);

    List<Author> findDistinctByNickNameIgnoreCaseAndPhoneOrderBySignDateDesc(String nickName, String phone);
    //Like 要針對查詢關鍵字前後+"%"
    List<Author> findByNickNameLike(String nickName);

    //定義JPQL語句  在參數內也可匹配百分號
    @Query("select a from Author a where a.phone like %?1%")
    List<Author> findByPhoneLike(String phone);

    @Query("select a.nickName, length(a.nickName) from Author a where a.nickName like %?1%")
    List<Object[]> findArray(String nickName);

    @Query("select a from Author a where a.nickName like %?1%")
    List<Author> findByNickName(String nickName, Sort sort);

    //定義JPQL語句  接收參數也可以用":自訂名稱" 需要再傳參數裡面@Param寫對應的名字
    @Query("select a from Author a where a.phone like %:phone% and a.phone like %:phone2%")
    List<Author> findByPhoneLike2(@Param("phone") String phone, @Param("phone2") String phone2);

    //nativeQuery = true 才會開啟SQL與句的使用  且sql語句的名稱 就要對應資料庫內的欄位名稱(否則找不到)
    @Query(value = "select * from author where nick_name like %?1%", nativeQuery = true)
    List<Author> findBySQL(String nickName);


    //Caused by: javax.persistence.TransactionRequiredException :  Executing an update/delete query
    @Transactional //update / delete 這種查詢的動作需要 @Transactional (必須要有事務處理的註解)
    @Modifying //update語句
    @Query("update Author a set a.nickName = ?1 where a.phone = ?2")
    int setNickName(String nickName, String phone);

}
//public interface AuthorRepository extends PagingAndSortingRepository<Author, Long> {
//}
