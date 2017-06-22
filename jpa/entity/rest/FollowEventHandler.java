package cn.springlogic.social.jpa.entity.rest;

import cn.springlogic.message.base.jpa.entity.Send;
import cn.springlogic.message.base.jpa.repository.MessageRepository;
import cn.springlogic.message.base.jpa.repository.SendRepository;
import cn.springlogic.message.domain.jpa.entity.MessageFollow;
import cn.springlogic.message.domain.jpa.repository.MessageFollowRepository;
import cn.springlogic.social.jpa.entity.Follow;
import cn.springlogic.social.jpa.repository.FollowRepository;
import com.fitcooker.app.BussinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeDelete;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by admin on 2017/5/15.
 */
@Component
@RepositoryEventHandler(Follow.class)
public class FollowEventHandler {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private SendRepository sendRepository;

    @Autowired
    private MessageFollowRepository messageFollowRepository;


    @HandleBeforeCreate
    public void beforeCreate(Follow follow) throws BussinessException {

        Follow byuserIdAndFollowUserId = followRepository.findByuserIdAndFollowUserId(follow.getUser().getId(), follow.getFollowUser().getId());

        if (byuserIdAndFollowUserId != null) {
            throw new BussinessException("您已经进行过关注操作!");
        }

    }

    @HandleAfterCreate
    public void afterCreate(Follow follow) throws BussinessException {


        Send send = new Send();
        send.setStatus(Send.EnumSendStatus.UNREAD.val);
        send.setUser(follow.getUser());

        MessageFollow message = new MessageFollow();
        message.setUser(follow.getFollowUser());
        message.setText("");
        message.setType(1);
        message.setFollow(follow);

        send.setMessage(message);

        messageRepository.save(message);
        sendRepository.save(send);
    }

    @HandleBeforeDelete
    public  void beforeDel(Follow follow) {

        MessageFollow byFollowId = messageFollowRepository.findByFollowId(follow.getId());
        if(byFollowId!=null){
            List<Send> sends = byFollowId.getSends();

            for (int i = 0; i < sends.size(); i++) {
                sendRepository.delete(sends.get(i));
            }

        }

        messageFollowRepository.delByFollowId(follow.getId());

    }

}
