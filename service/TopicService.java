package cn.springlogic.social.service;

import cn.springlogic.social.jpa.entity.Topic;

/**
 * Created by admin on 2017/4/24.
 */
public interface TopicService {


    Topic findByname(String name);

}
