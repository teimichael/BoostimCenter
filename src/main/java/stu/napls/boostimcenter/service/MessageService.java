package stu.napls.boostimcenter.service;

import org.springframework.data.domain.Pageable;
import stu.napls.boostimcenter.model.Message;

import java.util.List;

public interface MessageService {

    List<Message> findByConversationUuidOrderByTimestamp(String conversationUuid, Pageable pageable);


    Message update(Message message);
}
