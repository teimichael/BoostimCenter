package stu.napls.boostim.service.impl;

import org.springframework.stereotype.Service;
import stu.napls.boostim.core.dictionary.ConversationConst;
import stu.napls.boostim.model.Conversation;
import stu.napls.boostim.repository.ConversationRepository;
import stu.napls.boostim.service.ConversationService;

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
