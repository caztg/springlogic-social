package cn.springlogic.social.jpa.repository;

import cn.springlogic.social.jpa.entity.Follow;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

/**
 * Created by admin on 2017/4/27.
 */
@Configuration
@RepositoryRestResource(path="social:follow")
public interface FollowRepository extends JpaRepository<Follow,Integer> {
    //search/follow?projection=info&userId=&followUserId
    @RestResource(path = "follow",rel = "follow")
    public Follow findByuserIdAndFollowUserId(@Param("userId")int userId,@Param("followUserId")int followUserId);

    public List<Follow> findByUserId(@Param("userId")int userId);

}
