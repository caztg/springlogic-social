package cn.springlogic.social.jpa.entity;

import cn.springlogic.user.jpa.entity.User;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by admin on 2017/4/27.
 */
@Data
@Entity
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String memo;

    @ManyToOne(fetch = FetchType.EAGER,  // 指定user属性的抓取策略 FetchType.LAZY:延迟加载   FetchType.EAGER:立即加载
            targetEntity = User.class)// 指定关联的持久化类
    /** 生成关联的外键列 */
    @JoinColumn(name = "user_id", // 外键列的列名
            referencedColumnName = "id") // 指定引用user表的主键列
    @NotNull
    private User user;

    @ManyToOne(fetch = FetchType.EAGER,  // 指定user属性的抓取策略 FetchType.LAZY:延迟加载   FetchType.EAGER:立即加载
            targetEntity = User.class)// 指定关联的持久化类
    /** 生成关联的外键列 */
    @JoinColumn(name = "follow_user_id", // 外键列的列名
            referencedColumnName = "id") // 指定引用user表的主键列
    @NotNull
    private User followUser;

    @Column(name = "create_time")
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    private String status;

    // 是否互相关注
    @Transient
    private Boolean eachOther;

}
