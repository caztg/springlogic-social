package cn.springlogic.social.service.impl;

import cn.springlogic.social.jpa.entity.Tag;
import cn.springlogic.social.jpa.repository.TagRepository;
import cn.springlogic.social.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by admin on 2017/4/24.
 */
@Component
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository tagRepository;

    @Override
    public Tag save(Tag tag) {
        Tag save = tagRepository.save(tag);
        return save;
    }
}
