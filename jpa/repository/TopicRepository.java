package cn.springlogic.social.jpa.repository;

import cn.springlogic.social.jpa.entity.Topic;
import cn.springlogic.social.jpa.entity.rest.TopicProjection;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

/**
 * Created by chenan on 2017/4/21.
 */
@Configuration
@RepositoryRestResource(path="social:topics",excerptProjection = TopicProjection.class)
public interface TopicRepository extends JpaRepository<Topic,Integer> {

    //判断该list的size来辨别是否没有,而不是用null,因为查询出来就算没有,也会有该list的对象
    public Topic findByname(@Param("name")String name);





    //根据topic名称模糊查询出 topic ,设置的topicProjection还把它之下的饭圈数量统计了出来
    //@Query("select topic from Topic topic ,Tag t,Publishing p where p.id=t.publishing.id and t.topic.id=topic.id and topic.name LIKE CONCAT('%',:topic,'%')")
    @Query("select topic from Topic topic where topic.name LIKE CONCAT('%',:topic,'%')")
    @RestResource(path = "topic",rel = "topic")
    public List<Topic> getTopicsAndNums(@Param("topic") String topic);


    //根据topic名称模糊查询出 topic ,设置的topicProjection还把它之下的饭圈数量统计了出来
    @Query("select topic from Topic topic where topic.hot=1 and :topic IS NULL OR topic.name LIKE CONCAT('%',:topic,'%')")
    @RestResource(path = "hot_topic",rel = "hot_topic")
    public List<Topic> getHotTopicsAndNums(@Param("topic") String topic);



    @Query("select distinct topic from Topic topic,User u where  topic.name LIKE CONCAT('%',:name,'%') AND (:nickname IS NULL OR u.nickName LIKE CONCAT('%',:nickname,'%') AND u.id=topic.user.id ) AND ( :description IS NULL OR topic.description LIKE CONCAT('%',:description,'%') ) ORDER BY topic.createTime DESC ")
    Page<Topic> getTopics(@Param("name")String topicName,@Param("nickname")String nickname,@Param("description")String description,Pageable pageable);
    //where (:nickname is null OR topic.user.nickName LIKE CONCAT('%',:nickname,'%')) AND (:name IS NULL OR topic.name LIKE CONCAT('%',:name,'%')) AND (:description IS NULL OR topic.description LIKE CONCAT('%',:description,'%') )
    //@Param("nickname")String nickname,@Param("name")String topicName,@Param("description")String description,

    @Query("select distinct topic from Topic topic,User u where  topic.name LIKE CONCAT('%',:name,'%') AND (:nickname IS NULL OR u.nickName LIKE CONCAT('%',:nickname,'%') AND u.id=topic.user.id ) AND ( :description IS NULL OR topic.description LIKE CONCAT('%',:description,'%') ) ORDER BY topic.createTime ASC ")
    Page<Topic> getTopicsASC(@Param("name")String topicName,@Param("nickname")String nickname,@Param("description")String description,Pageable pageable);

    @Query("select topic from Topic topic where topic.user IS NULL and  topic.name LIKE CONCAT('%',:name,'%') and ( :description IS NULL OR topic.description LIKE CONCAT('%',:description,'%') ) order by topic.createTime DESC ")
    Page<Topic> getAdminTopicDESC(@Param("name")String name,@Param("description")String description, Pageable pageable);

    @Query("select topic from Topic topic where topic.user IS NULL and  topic.name LIKE CONCAT('%',:name,'%') and ( :description IS NULL OR topic.description LIKE CONCAT('%',:description,'%') ) order by topic.createTime ASC ")
    Page<Topic> getAdminTopicASC(@Param("name")String name,@Param("description")String description, Pageable pageable);

}
