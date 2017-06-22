package cn.springlogic.social.jpa.repository;

import cn.springlogic.collection.jpa.entity.Favor;
import cn.springlogic.social.jpa.entity.PublicationComment;
import cn.springlogic.social.jpa.entity.PublicationFavor;
import cn.springlogic.social.jpa.entity.rest.PublicationFavorProjection;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.method.P;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by admin on 2017/4/27.
 */
@Configuration
@RepositoryRestResource(path="social:publicationfavor",excerptProjection = PublicationFavorProjection.class)
public interface PublicationFavorRepository extends JpaRepository<PublicationFavor,Integer> {


    @RestResource(path = "a",rel ="a" ,exported = true)
    @Modifying
    @Transactional
    @Query("delete from Favor f where f.user.id=:userid and f.publication.id=:publicationid")
    public void deleteFavorByUserIdAndPublicationId(@Param("userid")Integer userId,@Param("publicationid")Integer publicationId);

    /*
     根据用户id找出该用户 所有的点赞
     */

    @Query("select f from Favor f where f.user.id=:userid")
    @RestResource(path = "cancel" ,rel = "cancel")
    public List<Favor> getFavorByuserId(@Param("userid")int userId);

    public List<PublicationFavor> findBypublicationId(int publicationId);

    public PublicationFavor findByPublicationIdAndUserId(@Param("publicationId")int publicationId,@Param("userId")int userId);


    @javax.persistence.OrderBy( "create_time desc " )
    public Page<PublicationFavor> findByPublicationIdOrderByCreateTimeDesc(@Param("pid")int publicationId, Pageable pageable);

    @RestResource(path = "favors",rel = "favors")
    public Page<PublicationFavor> findByPublicationId(@Param("publicationId")int publicationId, Pageable pageable);

    @Modifying
    @Transactional
    @Query("delete from Favor f where f.publication.id=:publicationid")
    void deleteFavorByPublicationId(@Param("publicationid")Integer publicationId);


}
