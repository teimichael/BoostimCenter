package stu.napls.boostimcenter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import stu.napls.boostimcenter.model.Conversation;

import java.util.List;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {

    Conversation findByUuid(String uuid);

    Conversation findByUuidAndType(String uuid, int type);

    List<Conversation> findByUsersContaining(String uuid);

    List<Conversation> findByTypeAndUsersContaining(int type, String uuid);

}

