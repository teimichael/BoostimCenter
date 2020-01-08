package stu.napls.boostim.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import stu.napls.boostim.core.dictionary.APIConst;
import stu.napls.boostim.core.dictionary.ConversationConst;
import stu.napls.boostim.core.exception.Assert;
import stu.napls.boostim.core.response.Response;
import stu.napls.boostim.model.Conversation;
import stu.napls.boostim.model.Message;
import stu.napls.boostim.model.User;
import stu.napls.boostim.service.ConversationService;
import stu.napls.boostim.service.MessageService;
import stu.napls.boostim.service.UserService;
import stu.napls.boostim.util.ChatUtil;
import stu.napls.boostim.util.SimpMessageHeaderAccessorFactory;

import javax.annotation.Resource;
import java.util.UUID;

@Controller
public class PrivateChatController {

    private static final Logger logger = LoggerFactory.getLogger(PrivateChatController.class);

    @Resource
    private SimpMessagingTemplate simpMessagingTemplate;

    @Resource
    private UserService userService;

    @Resource
    private MessageService messageService;

    @Resource
    private ConversationService conversationService;

    @MessageMapping("/private/send")
    @SendToUser(APIConst.PRIVATE_CHANNEL)
    public Response sendPrivate(Message message, SimpMessageHeaderAccessor accessor) {
        User sender = userService.findUserBySessionId(accessor.getSessionId());
        Assert.isTrue(sender != null && message.getSender().equals(sender.getUuid()), "Unauthorized channel.");
        User receiver = userService.findUserByUuid(message.getReceiver());
        Assert.notNull(receiver, "Receiver does not exist.");

        // Set unique fields
        message.setUuid(UUID.randomUUID().toString());
        message.setTimestamp(System.currentTimeMillis());

        // Get conversation
        Conversation conversation = getConversation(sender.getUuid(), receiver.getUuid());

        // Set conversation UUID
        message.setConversationUuid(conversation.getUuid());

        // Send message
        if (receiver.getSessionId() != null) {
            // Receiver is online
            simpMessagingTemplate.convertAndSendToUser(receiver.getSessionId(), APIConst.PRIVATE_CHANNEL, Response.success(message), SimpMessageHeaderAccessorFactory.getMessageHeaders(receiver.getSessionId()));
        } else {
            // Receiver is offline
            // Update unread list
            receiver.setUnreadList(ChatUtil.getNewUnreadList(receiver.getUnreadList(), conversation.getUuid(), ChatUtil.UNREAD_ADD));
            userService.update(receiver);
        }

        // Message persistence
        message = messageService.update(message);

        conversation.setLastMessage(message);

        // Conversation persistence
        conversationService.update(conversation);

        return Response.success(message);
    }

    private Conversation getConversation(String senderUuid, String receiverUuid) {
        Conversation conversation = conversationService.findPrivateByUserUuids(senderUuid, receiverUuid);

        // Create new conversation
        if (conversation == null) {
            conversation = new Conversation();
            conversation.setUuid(UUID.randomUUID().toString());
            conversation.setType(ConversationConst.TYPE_PRIVATE);
            conversation.setUsers(senderUuid + ConversationConst.SPLITTER + receiverUuid);
            conversation = conversationService.update(conversation);
        }

        return conversation;
    }

}
