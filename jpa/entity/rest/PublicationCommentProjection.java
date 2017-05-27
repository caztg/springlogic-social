package cn.springlogic.social.jpa.entity.rest;

import cn.springlogic.blog.jpa.entity.rest.UserProjection;
import cn.springlogic.social.jpa.entity.Publication;
import cn.springlogic.social.jpa.entity.PublicationComment;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;

/**
 * Created by admin on 2017/4/27.
 */
@Projection(name = "commentinfo", types = {PublicationComment.class})
public interface PublicationCommentProjection {

    int getId();

    String getContent();

    UserProjection getUser();

    Date getCreateTime();

   ReplyCommentProjection getReplyComment();

   Publication4CommentProjection getPublication();

}
