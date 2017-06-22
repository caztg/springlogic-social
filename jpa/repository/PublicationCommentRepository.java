package cn.springlogic.social.jpa.repository;

import cn.springlogic.social.jpa.entity.PublicationComment;
import org.hibernate.annotations.OrderBy;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.OrderColumn;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by admin on 2017/4/27.
 */
@Configuration
@RepositoryRestResource(path="social:publicationcomment")
public interface PublicationCommentRepository extends JpaRepository<PublicationComment,Integer> {
     //加orderby内存溢出
    //public List<PublicationComment> findBypublicationIdOrderByCreateTimeDesc(int publicationId);
     public List<PublicationComment> findBypublicationId(int publicationId);
    public List<PublicationComment> findFirst2BypublicationId(int publicationId);
    //public List<PublicationComment> findFirst2BypublicationIdOrderByCreateTimeDesc(int publicationId);


    @RestResource(path = "comments",rel = "comments")
    @javax.persistence.OrderBy( "create_time desc " )
    public Page<PublicationComment> findByPublicationIdOrderByCreateTimeDesc(@Param("pid")int publicationId, Pageable pageable);

    @Query("select distinct c from Comment c  where (:content IS NULL or c.content LIKE CONCAT('%',:content,'%') ) and (:nickname IS NULL OR c.user.nickName LIKE CONCAT('%',:nickname,'%') ) and (:phone IS NULL OR c.user.phone LIKE CONCAT('%',:phone,'%') ) order by c.createTime DESC ")
    @RestResource(path = "all",rel = "all")
    public Page<PublicationComment> findByContentAndNickName(@Param("content")String content,@Param("nickname")String nickname,@Param("phone")String phone, Pageable pageable);

    @Modifying
    @Transactional
    @Query("delete from Comment c where c.publication.id=:publicationid")
    void deleteCommentByPublicationId(@Param("publicationid")Integer publicationId);
}
