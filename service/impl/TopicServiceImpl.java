package cn.springlogic.social.service.impl;

import cn.springlogic.social.jpa.entity.Topic;
import cn.springlogic.social.jpa.repository.TopicRepository;
import cn.springlogic.social.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by admin on 2017/4/24.
 */
@Component
public class TopicServiceImpl implements TopicService {

    @Autowired
    private TopicRepository topicRepository;

    @Override
    public Topic findByname(String name) {
        return topicRepository.findByname(name);
    }
}
