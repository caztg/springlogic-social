package cn.springlogic.social.jpa.entity.rest;

import cn.springlogic.message.jpa.entity.Message;
import cn.springlogic.message.jpa.entity.Send;
import cn.springlogic.message.jpa.repository.MessageRepository;
import cn.springlogic.message.jpa.repository.SendRepository;
import cn.springlogic.social.jpa.entity.PublicationComment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

/**
 * Created by admin on 2017/5/15.
 */
@Component
@RepositoryEventHandler(PublicationComment.class)
public class PublicationCommentEventHandler {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private SendRepository sendRepository;

    @HandleAfterCreate
    public void afterCreate(PublicationComment  comment){

        Send send=new Send();

        send.setStatus(Send.EnumSendStatus.UNREAD.val);

        send.setUser(comment.getPublication().getUser());

        Message message=new Message();

        message.setUser(comment.getUser());

        message.setText(comment.getContent());

        message.setType(2);

        send.setMessage(message);

        messageRepository.save(message);

        sendRepository.save(send);
    }

}
