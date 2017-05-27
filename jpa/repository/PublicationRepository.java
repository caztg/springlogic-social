package cn.springlogic.social.jpa.repository;

import cn.springlogic.social.jpa.entity.Publication;
import cn.springlogic.social.jpa.entity.rest.PublicationProjection;
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
 * Created by admin on 2017/4/21.
 */
@Configuration
@RepositoryRestResource(path="social:publication",excerptProjection = PublicationProjection.class)
public interface PublicationRepository extends JpaRepository<Publication,Integer> {

    /**
     * 广场的饭圈:
     * 根据话题搜索publication(饭圈)  按最新排序
     */

    //@Query("select distinct publication from Publication publication,Tag t where :topic IS NULL OR t.topic.name LIKE CONCAT('%',:topic,'%')  AND t in elements(publication.tags) order by publication.createTime DESC")
    @Query("select distinct publication from Publication publication,Tag t where :topic IS NULL OR t.topic.name LIKE CONCAT('%',:topic,'%')  and t.publication.id=publication.id order by publication.createTime DESC")
    @RestResource(path = "all",rel = "all")
    public Page<Publication> findByAll(@Param("topic")String topic, Pageable pageable);

    /**
     * 关注者的饭圈
     * 默认选出关注人的饭圈
     * 可以根据话题搜索
     */
   // @Query("select distinct publication from Publication publication ,Follow f,Tag t where f.user.id=:id and publication.user.id=f.followUser.id and :topic IS NULL OR t.topic.name LIKE CONCAT('%',:topic,'%') and t in elements(publication.tags) and t.publication.id=f.followUser.id order by publication.createTime DESC  ")
    @Query("select distinct publication from Publication publication ,Follow f,Tag t where  f.user.id=:id and publication.user.id !=:id  and :topic IS NULL OR t.topic.name LIKE CONCAT('%',:topic,'%') and t.publication.user.id!=:id and t.publication.user.id=f.followUser.id  and t.publication.id=publication.id order by publication.createTime DESC  ")
    @RestResource(path = "follow",rel = "follow")
    public Page<Publication> findByFollow(@Param("topic")String topic, @Param("id")int userId, Pageable pageable);

    /**
     * 读取一个用户的所有饭圈
     * @param userId
     * @param pageable
     * @return
     */
    @Query("select  publication from Publication publication where publication.user.id=:id ")
    @RestResource(path = "user",rel = "user")
    public Page<Publication> findByUserId(@Param("id")int userId,Pageable pageable);


    @Query("select distinct publication from Publication publication,Tag t where t.publication.id=publication.id and publication.id=:id order by publication.createTime DESC")
    @RestResource(path = "one2",rel = "one2")
    public Publication findOneById(@Param("id")int id);


    @Query("select distinct publication from Publication publication,Tag t where t.publication.id=publication.id and publication.id=:id order by publication.createTime DESC")
    @RestResource(path = "one",rel = "one")
    public Publication findOneById2(@Param("id")int id);


    @Query("select distinct publication from Publication publication,Tag t where publication.user.nickName LIKE CONCAT('%',:nickname,'%') and publication.user.phone LIKE CONCAT('%',:phone,'%') and publication.article.content LIKE CONCAT('%',:content,'%')  and:topic IS NULL OR t.topic.name LIKE CONCAT('%',:topic,'%') and t.publication.id=publication.id  order by publication.createTime DESC ")
    public Page<Publication> findByAllAdmin(@Param("topic")String topic,@Param("nickname")String nickname,@Param("phone")String phone, @Param("content")String content, Pageable pageable);

    @Query("select distinct publication from Publication publication,Tag t where publication.user.nickName LIKE CONCAT('%',:nickname,'%') and publication.user.phone LIKE CONCAT('%',:phone,'%') and publication.article.content LIKE CONCAT('%',:content,'%')  and:topic IS NULL OR t.topic.name LIKE CONCAT('%',:topic,'%') and t.publication.id=publication.id  order by publication.createTime ASC ")
    public Page<Publication> findByAllAdminASC(@Param("topic")String topic,@Param("nickname")String nickname,@Param("phone")String phone, @Param("content")String content, Pageable pageable);
    // and t.publication.id=publication.id 保证了  :topic IS NULL OR t.topic.name LIKE CONCAT('%',:topic,'%') 这个生效


    @Query("select distinct publication from Publication publication,Tag t where :topic IS NULL OR t.topic.name LIKE CONCAT('%',:topic,'%')  and t.publication.id=publication.id order by publication.createTime DESC")
    public List<Publication> findListByAll(@Param("topic")String topic);

}
