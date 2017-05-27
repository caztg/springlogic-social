package cn.springlogic.social.jpa.entity.rest;

import cn.springlogic.social.jpa.entity.Follow;
import cn.springlogic.social.jpa.entity.PublicationComment;
import org.springframework.data.rest.core.config.Projection;

/**
 * Created by admin on 2017/5/23.
 */
@Projection(name = "info", types = {Follow.class})
public interface FollowProjection {

    int getId();

}
