package cn.springlogic.social.jpa.entity.rest;

import cn.springlogic.blog.jpa.entity.rest.ArticleProjection;
import cn.springlogic.blog.jpa.entity.rest.UserProjection;
import cn.springlogic.collection.jpa.entity.Favor;
import cn.springlogic.collection.jpa.entity.rest.FavorInfoProjection;
import cn.springlogic.social.jpa.entity.Follow;
import cn.springlogic.social.jpa.entity.Publication;
import cn.springlogic.social.jpa.entity.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;
import java.util.List;

/** 用于饭圈列表
 * Created by chenan on 2017/4/27.
 */
@Projection(name = "publishinginfo", types = {Publication.class})
public interface PublicationProjection {

    int getId();

    UserProjection getUser();

    Date getCreateTime();

    ArticleProjection getArticle();

    @Value("#{target.publicationFavors.size()}")
    int getFavorNum();

    @Value("#{target.publicationComments.size()}")
    int getCommentNum();


    List<PublicationCommentProjection> getpublicationComments();

    List<TagProjection> getTags();

    FavorInfoProjection getFavor();

    Follow getFollow();

}
