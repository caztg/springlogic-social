package cn.springlogic.social.jpa.repository;

import cn.springlogic.social.jpa.entity.Tag;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by admin on 2017/4/21.
 */
@Configuration
@RepositoryRestResource(path="social:tag")
public interface TagRepository extends JpaRepository<Tag,Integer> {


    public List<Tag> findBypublicationId(int id);

    @Modifying
    @Transactional
    @Query("delete from Tag t where t.publication.id=:publicationid")
    void deleteTagByPublicationId(@Param("publicationid")Integer publicationId);

}
