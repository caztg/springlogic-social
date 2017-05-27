package cn.springlogic.social.jpa.entity.rest;

import cn.springlogic.message.jpa.entity.Message;
import cn.springlogic.message.jpa.entity.Send;
import cn.springlogic.message.jpa.repository.MessageRepository;
import cn.springlogic.message.jpa.repository.SendRepository;
import cn.springlogic.social.jpa.entity.PublicationFavor;
import cn.springlogic.social.jpa.repository.PublicationFavorRepository;
import com.fitcooker.app.BussinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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
    public void beforeCreate(PublicationFavor  favor)throws BussinessException{
        PublicationFavor byPublicationIdAndUserId = publicationFavorRepository.findByPublicationIdAndUserId(favor.getPublication().getId(), favor.getUser().getId());
        if(byPublicationIdAndUserId!=null){
            throw new BussinessException("您已经对该饭圈进行过点赞操作!");
        }

    }

    @HandleAfterCreate
    public void afterCreate(PublicationFavor  favor)  {


        Send send=new Send();

        send.setStatus(Send.EnumSendStatus.UNREAD.val);

        send.setUser(favor.getPublication().getUser());

        Message message=new Message();

        message.setUser(favor.getUser());

        message.setText("");

        message.setType(3);

        send.setMessage(message);

        messageRepository.save(message);

        sendRepository.save(send);
    }

}
