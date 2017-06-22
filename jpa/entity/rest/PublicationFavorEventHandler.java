package cn.springlogic.social.jpa.entity.rest;

import cn.springlogic.message.base.jpa.entity.Send;
import cn.springlogic.message.base.jpa.repository.MessageRepository;
import cn.springlogic.message.base.jpa.repository.SendRepository;
import cn.springlogic.message.domain.jpa.entity.MessagePublicationFavor;
import cn.springlogic.social.jpa.entity.PublicationFavor;
import cn.springlogic.social.jpa.repository.PublicationFavorRepository;
import com.fitcooker.app.BussinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

/**
 * Created by admin on 2017/5/15.
 */
@Component
@RepositoryEventHandler(PublicationFavor.class)
public class PublicationFavorEventHandler {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private SendRepository sendRepository;

    @Autowired
    private PublicationFavorRepository publicationFavorRepository;


    @HandleBeforeCreate
    public void beforeCreate(PublicationFavor favor) throws BussinessException {
        PublicationFavor byPublicationIdAndUserId = publicationFavorRepository.findByPublicationIdAndUserId(favor.getPublication().getId(), favor.getUser().getId());
        if (byPublicationIdAndUserId != null) {
            throw new BussinessException("您已经对该饭圈进行过点赞操作!");
        }

    }

    @HandleAfterCreate
    public void afterCreate(PublicationFavor favor) {
        if (favor.getPublication().getUser().getId() != favor.getUser().getId()) {
            Send send = new Send();
            send.setStatus(Send.EnumSendStatus.UNREAD.val);
            send.setUser(favor.getPublication().getUser());

            MessagePublicationFavor message = new MessagePublicationFavor();
            message.setUser(favor.getUser());
            message.setText("");
            message.setType(3);
            //message.setPublication(favor.getPublication());
            message.setPublicationId(favor.getPublication().getId());

            send.setMessage(message);

            messageRepository.save(message);
            sendRepository.save(send);
        }
    }

}
