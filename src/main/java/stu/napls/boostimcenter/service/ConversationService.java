package stu.napls.boostimcenter.service;

import stu.napls.boostimcenter.model.Conversation;

import java.util.List;

public interface ConversationService {

    Conversation findById(long id);

    Conversation findByUuid(String uuid);

    Conversation findByUuidAndType(String uuid, int type);

    List<Conversation> findByUserUuid(String uuid);

    Conversation findPrivateByUserUuids(String uuid0, String uuid1);

    Conversation update(Conversation conversation);
}
