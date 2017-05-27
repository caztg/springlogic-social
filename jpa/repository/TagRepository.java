package cn.springlogic.social.jpa.repository;

import cn.springlogic.social.jpa.entity.Tag;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Created by admin on 2017/4/21.
 */
@Configuration
@RepositoryRestResource(path="social:tag")
public interface TagRepository extends JpaRepository<Tag,Integer> {


    public List<Tag> findBypublicationId(int id);
}
