package cn.springlogic.social.jpa.entity.rest;

import cn.springlogic.social.jpa.entity.Topic;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fitcooker.app.AppDataPreFixSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

/**
 * Created by admin on 2017/4/26.
 */
@Projection(name = "topic",types = {Topic.class})
public interface TopicProjection {

    public int getId();

    public String getName();

    @Value("#{target.tags.size()}")
    int getNum();

    String getHot();

    @JsonSerialize(using = AppDataPreFixSerializer.class)
    String getImage();

}
