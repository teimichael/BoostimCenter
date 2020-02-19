package stu.napls.boostimcenter.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import stu.napls.boostimcenter.model.Message;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByConversationUuidOrderByTimestampDesc(String conversationId, Pageable pageable);


}

