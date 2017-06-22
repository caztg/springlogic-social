package cn.springlogic.social.jpa.entity.rest;

import cn.springlogic.social.jpa.entity.Topic;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fitcooker.app.serializer.AppDataPreFixSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;

/**
 * Created by admin on 2017/4/26.
 */
@Projection(name = "topic", types = {Topic.class})
public interface TopicProjection {

    public int getId();

    public String getName();

    @Value("#{target.tags.size()}")
    int getPublicationNums();

    String getHot();

    @JsonSerialize(using = AppDataPreFixSerializer.class)
    String getImage();

    String getDescription();

    Date getCreateTime();

    Date getUpdateTime();
}
