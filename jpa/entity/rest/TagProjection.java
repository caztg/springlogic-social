package cn.springlogic.social.jpa.entity.rest;

import cn.springlogic.social.jpa.entity.Tag;
import cn.springlogic.social.jpa.entity.Topic;
import org.springframework.data.rest.core.config.Projection;

/**
 * Created by admin on 2017/4/26.
 */
@Projection(name = "taginfo",types = {Tag.class})
public interface TagProjection {

    int getId();


    int getIndex();

    TopicProjection getTopic();
}
