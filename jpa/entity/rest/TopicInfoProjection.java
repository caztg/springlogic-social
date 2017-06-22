package cn.springlogic.social.jpa.entity.rest;

import cn.springlogic.blog.jpa.entity.rest.UserProjection;
import cn.springlogic.social.jpa.entity.Topic;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fitcooker.app.serializer.AppDataPreFixSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;

/**
 * Created by admin on 2017/5/11.
 */
@Projection(name = "topicinfo",types = {Topic.class})
public interface TopicInfoProjection {

     int getId();

     String getName();

    //@Value("#{target.tags.size()}")
    //int getNum();
    @Value("#{target.tags.size()}")
    int getPublicationNums();

    int getFavorNums();

    UserProjection getUser();

    Date getCreateTime();

    String getDescription();

    String getHot();

    @JsonSerialize(using = AppDataPreFixSerializer.class)
    String getImage();
}
