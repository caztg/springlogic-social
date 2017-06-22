package cn.springlogic.social.jpa.entity;

import cn.springlogic.user.jpa.entity.User;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fitcooker.app.serializer.AppDataPreFixSerializer;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by admin on 2017/4/20.
 */
@Data
@Entity
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;
    @JsonSerialize(using = AppDataPreFixSerializer.class)
    private String image;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY,  // 指定user属性的抓取策略 FetchType.LAZY:延迟加载   FetchType.EAGER:立即加载
            targetEntity = User.class)// 指定关联的持久化类
    /** 生成关联的外键列 */
    @JoinColumn(name = "user_id", // 外键列的列名
            referencedColumnName = "id") // 指定引用user表的主键列
    private User user;

    @Column(name = "create_time")
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @Column(name = "update_time")
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;



    @OneToMany(
            targetEntity = Tag.class,
    mappedBy = "topic")
    private List<Tag> tags=new ArrayList<>();

    //删除该话题, 但在Tag表设置为null
    @PreRemove
    private void preRemove(){
        for (Tag t:tags) {
            t.setTopic(null);
        }

    }

   //1 为热门话题
   private String hot;

   @Transient
   private int publicationNums;

   @Transient
    private int favorNums;
}
