package cn.springlogic.social.jpa.entity.rest;

import cn.springlogic.message.jpa.entity.Message;
import cn.springlogic.message.jpa.entity.Send;
import cn.springlogic.message.jpa.repository.MessageRepository;
import cn.springlogic.message.jpa.repository.SendRepository;
import cn.springlogic.social.jpa.entity.Follow;
import cn.springlogic.social.jpa.entity.PublicationFavor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

/**
 * Created by admin on 2017/5/15.
 */
@Component
@RepositoryEventHandler(Follow.class)
public class FollowEventHandler {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private SendRepository sendRepository;

    @HandleAfterCreate
    public void afterCreate(Follow  follow){

        Send send=new Send();

        send.setStatus(Send.EnumSendStatus.UNREAD.val);

        send.setUser(follow.getUser());

        Message message=new Message();

        message.setUser(follow.getFollowUser());

        message.setText("");

        message.setType(1);

        send.setMessage(message);

        messageRepository.save(message);

        sendRepository.save(send);
    }

}
