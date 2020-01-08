package stu.napls.boostim.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import stu.napls.boostim.config.property.AppServer;

import javax.annotation.Resource;

/**
 * @Author Tei Michael
 * @Date 12/29/2019
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Resource
    private AppServer appServer;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/notify", "/private", "/group");
        config.setApplicationDestinationPrefixes("/to");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/boostim")
                .setAllowedOrigins("*")
                .withSockJS();
    }

}
