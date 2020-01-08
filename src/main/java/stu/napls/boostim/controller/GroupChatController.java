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
public class GroupChatController {

    private static final Logger logger = LoggerFactory.getLogger(GroupChatController.class);

    @Resource
    private SimpMessagingTemplate simpMessagingTemplate;

    @Resource
    private UserService userService;

    @Resource
    private MessageService messageService;

    @Resource
    private ConversationService conversationService;

    @MessageMapping("/group/send")
    @SendToUser(APIConst.GROUP_CHANNEL)
    public Response sendGroup(Message message, SimpMessageHeaderAccessor accessor) {
        User sender = userService.findUserBySessionId(accessor.getSessionId());
        Assert.isTrue(sender != null && message.getSender().equals(sender.getUuid()), "Unauthorized channel.");
        Conversation conversation = conversationService.findByUuid(message.getReceiver());
        Assert.notNull(conversation, "Group does not exist.");

        // Set unique fields
        message.setUuid(UUID.randomUUID().toString());
        message.setTimestamp(System.currentTimeMillis());

        // Set conversation UUID
        message.setConversationUuid(conversation.getUuid());

        // Send messages
        String[] users = conversation.getUsers().split(ConversationConst.SPLITTER);
        User receiver;
        String receiverSessionId;
        for (int i = 0; i < users.length; i++) {
            receiver = userService.findUserByUuid(users[i]);
            receiverSessionId = receiver.getSessionId();
            if (receiverSessionId != null) {
                // Receiver is online
                if (!receiverSessionId.equals(sender.getSessionId())) {
                    simpMessagingTemplate.convertAndSendToUser(receiverSessionId, APIConst.GROUP_CHANNEL, Response.success(message), SimpMessageHeaderAccessorFactory.getMessageHeaders(receiverSessionId));
                }
            } else {
                // Receiver is offline
                // Update unread list
                receiver.setUnreadList(ChatUtil.getNewUnreadList(receiver.getUnreadList(), conversation.getUuid(), ChatUtil.UNREAD_ADD));
                userService.update(receiver);
            }
        }

        // Message persistence
        message = messageService.update(message);

        conversation.setLastMessage(message);

        // Conversation persistence
        conversationService.update(conversation);

        return Response.success(message);
    }

}
