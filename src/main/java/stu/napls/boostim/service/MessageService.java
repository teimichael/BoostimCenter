package stu.napls.boostim.service;

import stu.napls.boostim.model.Message;

import java.util.List;

public interface MessageService {

    List<Message> findByConversationUuid(String conversationUuid);

    Message update(Message message);
}
