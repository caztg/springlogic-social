package cn.springlogic.social.jpa.entity.rest;

import cn.springlogic.social.jpa.entity.Publication;
import org.springframework.data.rest.core.config.Projection;

/**
 * Created by admin on 2017/5/15.
 */
@Projection(name = "publication4comment", types = {Publication.class})
public interface Publication4CommentProjection {

    int getId();
}
