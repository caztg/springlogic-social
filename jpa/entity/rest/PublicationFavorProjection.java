package cn.springlogic.social.jpa.entity.rest;

import cn.springlogic.blog.jpa.entity.rest.UserProjection;
import cn.springlogic.social.jpa.entity.Follow;
import cn.springlogic.social.jpa.entity.PublicationComment;
import cn.springlogic.social.jpa.entity.PublicationFavor;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;

/**
 * Created by admin on 2017/4/27.
 */
@Projection(name = "favorinfo", types = {PublicationFavor.class})
public interface PublicationFavorProjection {

    int getId();

    UserProjection getUser();

    Date getCreateTime();

    Follow getFollow();
}
