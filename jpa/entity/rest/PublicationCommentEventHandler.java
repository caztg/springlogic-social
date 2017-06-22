package cn.springlogic.social.jpa.entity.rest;

import cn.springlogic.message.base.jpa.entity.Send;
import cn.springlogic.message.base.jpa.repository.MessageRepository;
import cn.springlogic.message.base.jpa.repository.SendRepository;
import cn.springlogic.message.domain.jpa.entity.MessagePublicationComment;
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
    public void afterCreate(PublicationComment comment) {

        if(comment.getPublication().getUser().getId()!=comment.getUser().getId()) {
            Send send = new Send();
            send.setStatus(Send.EnumSendStatus.UNREAD.val);
            send.setUser(comment.getPublication().getUser());

            MessagePublicationComment message = new MessagePublicationComment();
            message.setUser(comment.getUser());
            message.setText(comment.getContent());
            message.setType(2);
            //message.setPublication(comment.getPublication());
            message.setPublicationId(comment.getPublication().getId());

            send.setMessage(message);

            messageRepository.save(message);
            sendRepository.save(send);
        }
    }

}
