package stu.napls.boostimcenter.service.impl;

import org.springframework.stereotype.Service;
import stu.napls.boostimcenter.core.dictionary.ConversationConst;
import stu.napls.boostimcenter.model.Conversation;
import stu.napls.boostimcenter.repository.ConversationRepository;
import stu.napls.boostimcenter.service.ConversationService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service("conversationService")
public class ConversationServiceImpl implements ConversationService {

    @Resource
    private ConversationRepository conversationRepository;

    @Override
    public Conversation findById(long id) {
        Conversation conversation = null;
        Optional<Conversation> result = conversationRepository.findById(id);
        if (result.isPresent()) {
            conversation = result.get();
        }
        return conversation;
    }

    @Override
    public Conversation findByUuid(String uuid) {
        return conversationRepository.findByUuid(uuid);
    }

    @Override
    public Conversation findByUuidAndType(String uuid, int type) {
        return conversationRepository.findByUuidAndType(uuid, type);
    }

    @Override
    public List<Conversation> findByUserUuid(String uuid) {
        return conversationRepository.findByUsersContaining(uuid);
    }

    @Override
    public Conversation findPrivateByUserUuids(String uuid0, String uuid1) {
        List<Conversation> conversations = conversationRepository.findByTypeAndUsersContaining(ConversationConst.TYPE_PRIVATE, uuid0);
        Conversation result = null;

        for (Conversation conversation :
                conversations) {
            if (conversation.getUsers().contains(uuid1)) {
                result = conversation;
                break;
            }
        }

        return result;
    }

    @Override
    public Conversation update(Conversation conversation) {
        return conversationRepository.save(conversation);
    }
}
