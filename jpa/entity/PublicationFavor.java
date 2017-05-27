package cn.springlogic.social.jpa.entity;

import cn.springlogic.collection.jpa.entity.Favor;
import lombok.Data;
import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

/**
 * Created by admin on 2017/4/27.
 */

@Entity(name = "PublicationFavor")
@Data
@DiscriminatorValue(value = "PublicationFavor")
public class PublicationFavor extends Favor{


    @ManyToOne(fetch= FetchType.EAGER,  // 指定user属性的抓取策略 FetchType.LAZY:延迟加载   FetchType.EAGER:立即加载
            targetEntity=Publication.class)// 指定关联的持久化类
    /** 生成关联的外键列 */
    @JoinColumn(name="publication_id", // 外键列的列名
            referencedColumnName="id") // 指定引用user表的主键列
    @Cascade(CascadeType.ALL)
    @NotNull
    private Publication publication;

    @Transient
    private Follow follow;
}
