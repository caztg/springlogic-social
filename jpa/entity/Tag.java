package cn.springlogic.social.jpa.entity;

import lombok.Data;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;

/**
 * Created by admin on 2017/4/20.
 */
@Data
@Entity
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "t_index")
    private String index;

    public String getIndex(){
        if(this.index!=null) {
            return this.index;
        }else {
            return "-1";
        }
    }

    @ManyToOne( fetch = FetchType.EAGER,
            cascade=CascadeType.PERSIST, // 指定topic属性的抓取策略 FetchType.LAZY:延迟加载   FetchType.EAGER:立即加载
            targetEntity = Topic.class)// 指定关联的持久化类
    /** 生成关联的外键列 */
    @JoinColumn(name = "topic_id", // 外键列的列名
            referencedColumnName = "id" ) // 指定引用表的主键列

    private Topic topic;


    @ManyToOne(cascade = CascadeType.PERSIST,
            targetEntity = Publication.class)// 指定关联的持久化类
    /** 生成关联的外键列 */
    @JoinColumn(name = "publication_id", // 外键列的列名
            referencedColumnName = "id") // 指定引用表的主键列
    private Publication publication;

}
