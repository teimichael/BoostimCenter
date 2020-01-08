package stu.napls.boostim.service;

import stu.napls.boostim.model.Conversation;

import java.util.List;

public interface ConversationService {

    Conversation findById(long id);

    Conversation findByUuid(String uuid);

    List<Conversation> findByUserUuid(String uuid);

    Conversation findPrivateByUserUuids(String uuid0, String uuid1);

    Conversation update(Conversation conversation);
}
