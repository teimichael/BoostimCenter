package stu.napls.boostim.util;

import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;

public class SimpMessageHeaderAccessorFactory {

    public static MessageHeaders getMessageHeaders(String sessionId) {
        SimpMessageHeaderAccessor simpMessageHeaderAccessor = SimpMessageHeaderAccessor
                .create(SimpMessageType.MESSAGE);
        simpMessageHeaderAccessor.setSessionId(sessionId);
        simpMessageHeaderAccessor.setLeaveMutable(true);
        return simpMessageHeaderAccessor.getMessageHeaders();
    }

}
