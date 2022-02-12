package com.leo.springbootjpademo.domain;

import javax.persistence.*;
import java.util.Date;


/**
 * 關於實體類的注入
 * Entity:  標示此類為數據模型類 應用於實體類，表明該實體類被JPA管理，將映射到指定的數據庫表
 * Table:   應用於實體類，通過 name 屬性指定對應該實體類映射表的名字
 * Id:      應用於實體類的屬性或者屬性對應的getter方法，表示該屬性映射為數據庫表的主鍵
 * GeneratedValue: 跟Id一同使用，表示主鍵的生成策略，通過strategy屬性設置。
 *   JPA的生成策略有：
 *      AUTO - JPA自動選擇合適的策略，為默認選項
 *      IDENTITY - 採用數據庫ID自動遞增的方式來生成主鍵值，Oracle不支持這種方式
 *      SEQUENCE - 通過序列產生主鍵，通過@SequenceGenerator註解指定序列名，MySql不支持這種方式  => @SequenceGenerator(name = "Hello")
 *      Table - 採用表生成方式來生成主鍵值，這種方式比較通用，但是效率低 (但是方便移植,大致方式為數據庫多新增一個表,來紀載其他表的主鍵新增到哪裡了,故每次訪問都得要改這張表的值,所以效率低)
 *
 * Basic:   應用於屬性，表示該屬性映射到數據庫表，@Entity標註的實體類的所有屬性，默認即為@Basic
 *      fetch:屬性的讀取策略,有EAGER和LAZY 兩種取值，分別表示主動抓取和延遲抓取，默認為EAGER。 從資料庫取得物件時,備標註為LAZY的值不會馬上取得，當調用get方法的時候才會去資料庫取這個值
 *      optional:表示該屬性是否允許為null 默認為true
 * Column: 應用於實體類的屬性，可以指定資料庫表字段的名字和其他屬性。其屬性包括:
 *      name: 表示數據庫中該字段的名稱 默認情形屬性名稱一致 生成資料庫時的表就會依指定方式命名
 *      nullable: 表示該字段是否允許為null 默認為true
 *      unique: 表示該字段是否是唯一標示，默認為false
 *      length: 表示該字段的大小 僅對String 類型的字段有效
 *      insertable: 表示在ORM框架執行插入操作時，該字段是否出現INSETRT語句中 默認為true
 *      updateable: 表示在ORM框架執行更新操作時，該字段是否應該出現在UPDATE語句中，默認為true。對於一經過創建就不能更改的字段，該屬性非常有用，比如email屬性
 *      columnDefinition: 表示該字段在數據庫中的實際類型，通常ORM框架可以根據屬性類型自動判斷數據庫中字段的類型，但是依然有例外:
 *          Date 類型無法確定數據庫中字段類型究竟是DATE，TIME或是TIMESTAMP
 *          String 的默認映射類行為 VARCHAR，如果希望將String 類型映射到特定數據庫的BLOB 或TEXT字段類型 則需要進行設置
 * Transient: 應用在實體類屬性上，表示該屬性不會映射到數據庫表，JPA會忽略該屬性 (Transient意思是短暫,瞬時,表示這個屬性不要持久化了) 相當於實體類裡面的普通的屬性值
 * Temporal: 應用到實體類屬性上，表示該屬性映射到數據庫是一個時間類型，具體定義為:
 *      @Temporal(TemporalType.DATE) 映射為日期//date(只有日期)
 *      @Temporal(TemporalType.TIME) 映射為日期//time(只有時間)
 *      @Temporal(TemporalType.TIMESTAMP) 映射為日期//date time(日期+時間)
 * Lob: 應用到實體類屬性上，表示將屬性映射成數據庫支持的大物件類型，Clob或者Blog。其中:
 *      Clob(Character Large Objects)類型是長字符串類型，java.sql.Clob、Character[]、char[]和String 將被映射為Clob類型。
 *      Blob(Binary Large Objects)類型是字節類型, java.sql.Blob、Byte[]、byte[]和實現了Serializable接口的類型將被映射為Blob類型。
 *      因為這兩種類型的數據一般占用的內存空間比較大，所以通常使用延遲加載的方式，與@Basic標註同時使用，設置加仔方式為FetchType.LAZY。
 *
 * ****關於建構式
 *      JPA中 物件是由Hibernate為我們創建的，當我們通過ID來獲取某個實體的時候，這個實體給我們返回了這個物件的創建是由Hibernate內部通過反射技術來創建的，反射的時候用到了默認的建構式，
 *      所以這時候必須提供一個public的無傳參建構式。
 *
 */
@Entity
//@Table(name = "t_author") <--就強制對應到t_author這個名稱的表
public class Author {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickName;
//    @Basic(fetch = FetchType.LAZY,optional = false)
    private String phone;
    @Temporal(TemporalType.DATE)
    private Date signDate;

    //OneToOne => 一對一對應關係 會生成wallet的id walletId 在資料庫結構中也會加入外部索引鍵 且由Author來維護這個關係
    //TransientPropertyValueException: object references an unsaved transient instance - save the transient instance before flushing
    // ---cascade---
    // ==>需要聯級保存(cascade = CascadeType.PERSIST) 意思是 如果新增Author物件時因要一起新增Wallet物件, 這個動作才能使兩個物件同步"保存"到個別資料庫中 (預設是不使用的所以要額外設定)
    // ==>聯級更新(CascadeType.MERGE) 設置Author的Wallet 並保存時 兩個資料表的內容會同步"更新"
    // ==>聯級查詢(CascadeType.REFRESH) 使用一對一對應關係不用額外設置,就能做聯級查詢
    // ==>聯級刪除(CascadeType.REMOVE) 刪除Author時 一併刪除對應的Wallet
    //鳥哥說明: https://openhome.cc/Gossip/EJB3Gossip/CascadeTypeFetchType.html
    // ---optional--- false: 做Author物件的時候必須要傳Wallet的值, 即 Wallet物件不得為空
    // ---fetch---
    // ==>FetchType.EAGER 只要獲取Author物件時 就會獲取到Wallet物件的值
    // ==>FetchType.LAZY 除非真正要使用到該屬性的值，否則不會真正將資料從表格中載入物件，所以EntityManager後，才要載入該屬性值，就會發生例外錯誤，解決的方式 之一是在EntityManager關閉前取得資料，另一個方式則是標示為FetchType.EARGE， 表示立即從表格取得資料。
    //JoinColumn -> name => 自訂外鍵生成的名稱 referencedColumnName => 外鍵關聯要引用的值(如Wallet中的id)
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "author_wallet_id")
    private Wallet wallet;


    public Author(){
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getSignDate() {
        return signDate;
    }

    public void setSignDate(Date signDate) {
        this.signDate = signDate;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }
}
