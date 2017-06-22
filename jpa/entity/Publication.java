package cn.springlogic.social.jpa.entity;

import cn.springlogic.blog.jpa.entity.Article;
import cn.springlogic.collection.jpa.entity.Favor;
import cn.springlogic.user.jpa.entity.User;
import lombok.Data;
import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.*;

/**
 * Created by admin on 2017/4/20.
 */
@Data
@Entity
public class Publication {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER,  // 指定user属性的抓取策略 FetchType.LAZY:延迟加载   FetchType.EAGER:立即加载
            targetEntity = User.class)// 指定关联的持久化类
    /** 生成关联的外键列 */
    @JoinColumn(name = "user_id", // 外键列的列名
            referencedColumnName = "id") // 指定引用user表的主键列
    private User user;

    @ManyToOne(cascade = javax.persistence.CascadeType.ALL,// 指定article属性的抓取策略 FetchType.LAZY:延迟加载   FetchType.EAGER:立即加载
            targetEntity = Article.class)// 指定关联的持久化类
    /** 生成关联的外键列 */
    @JoinColumn(name = "article_id", // 外键列的列名
            referencedColumnName = "id") // 指定引用表的主键列
    private Article article;

    @Column(name = "create_time")
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    /*中间表多了一个index字段,所以需要自己创建中间表的实体类Tag*/
    @OneToMany(mappedBy = "publication",cascade = javax.persistence.CascadeType.ALL)
   // @OneToMany(mappedBy = "publication")
    private List<Tag> tags=new ArrayList<>();


     @OneToMany(fetch = FetchType.EAGER,cascade = javax.persistence.CascadeType.ALL,
            targetEntity = PublicationFavor.class,
    mappedBy = "publication")
     @Fetch(FetchMode.SUBSELECT)
    private List<PublicationFavor> publicationFavors =new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER,cascade = javax.persistence.CascadeType.ALL,
            targetEntity = PublicationComment.class,
            mappedBy = "publication")
    @Fetch(FetchMode.SUBSELECT)
    private List<PublicationComment> publicationComments =new ArrayList<>();

    @Transient
    private int publicationCommentsTotal;

    @Transient
    private int publicationFavorsTotal;

    @Transient
    private Favor favor;

    @Transient
    private Follow follow;
}
